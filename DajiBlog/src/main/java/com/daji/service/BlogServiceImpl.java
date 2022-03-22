package com.daji.service;

import com.daji.dao.BlogDao;
import com.daji.pojo.Blog;
import com.daji.pojo.Type;
import com.daji.utils.MarkdownUtils;
import com.daji.vo.BlogQuery;
import com.daji.vo.SearchBlog;
import com.daji.vo.ShowBlog;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    //这里是自动注入, 理论上就不用配置Bean了
    @Autowired
    private BlogDao blogDao;
    @Autowired
    @Qualifier("redisTemplate") //这里的意思是指定我们自己配置的RedisConfig.java 里面的那个redisTemplate。而不是用系统默认的
    private RedisTemplate redisTemplate;
    @Autowired
    public TypeService typeService;

    @Override
    public List<BlogQuery> queryAllBlog() {
        return blogDao.queryAllBlog();
    }

    @Override
    public ShowBlog getAndConvert(int id) {
        ShowBlog blog = blogDao.getBlogById(id);
        if (blog == null){
            System.out.println("该博客不存在");
        }
        String content = blog.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(s);
        return blog;
    }

    @Override
    public List<BlogQuery> queryAllBlogOrderByViews() {
        return blogDao.queryAllBlogOrderByViews();
    }

    //查询所有被推荐的博客（置顶模块） 按照update_time排序
    @Override
    public List<BlogQuery> queryAllRecommendBlog() {
        return blogDao.queryAllRecommendBlog();
    }

    @Override
    public List<BlogQuery> queryAllBlogByLimit(Map<String, Integer> map) {
        return blogDao.queryAllBlogByLimit(map);
    }
    //所有带 _Redis的方法，都是代替了上面的方法，和上面的方法功能一模一样，但是是从redis中取数据
    @Override
    public List<BlogQuery> queryAllBlogByLimit_Redis(Map<String, Integer> map) {

        System.out.println("执行了从Redis中取数据的方法");
        List<BlogQuery> blogQueries = new ArrayList<>();

        //得到redis中所有带有 blog 的键 的个数（Readme.md中有讲该方法）
        int size = redisTemplate.keys("*blog*").size();
        /*
            循环拿出来所有的blog
            为啥是 size+30  原因是数据库里面 blog:id  那个id  不是连续的，有可能出现断层
            就像老师印试卷，总是比着学生数目再多印几张一样。怕学生弄丢
         */
        for (int i = 0; i < size + 50; i++) {
            //在redis中取键值对
            Blog blog = (Blog) redisTemplate.opsForValue().get("blog:"+i);
            if (blog != null){
                BlogQuery blogQuery = new BlogQuery(    // 由于从redis查出来的是blog类型，需要手动给转换成BlogQuery类型
                   blog.getId(),blog.getTitle(),blog.getUpdateTime(),blog.isRecommend(),blog.isPublished(),blog.getTypeId(),typeService.getTypeByBlogId(blog.getId()),blog.getFlag(),blog.getFirstPicture(),blog.getDescription(),blog.getViews(),blog.getCommentCount()
                );
                blogQueries.add(blogQuery);
            }
        }
        /*
            接下来对查询结果进行排序
            需求：首先根据updatetime排序，然后根据recommend排序（置顶recommend优先）。都是降序排列

            原理：java8对List对象排序
            https://blog.csdn.net/weixin_44073321/article/details/90763324
            Java 8 中我们可以通过 `::` 关键字来访问类的构造方法，对象方法，静态方法。
         */
        //根据updatetime降序排序
        blogQueries.sort(Comparator.comparing(BlogQuery::getUpdateTime).reversed());
        //根据recommend降序排序
        blogQueries.sort(Comparator.comparing(BlogQuery::getRecommend).reversed());


        // 已经得到了最终版本的blogQueries，然后进行分页 参见下一个方法，就有该逻辑
        int startIndex = map.get("startIndex");
        int pageSize = map.get("pageSize");

        int lastIndex = startIndex + pageSize;
        if (lastIndex > blogQueries.size())
            lastIndex = blogQueries.size();
        try {
            blogQueries.subList(startIndex, lastIndex);
        } catch (Exception e) {
            blogQueries.clear();
            return blogQueries;
        }
        return blogQueries.subList(startIndex, lastIndex);
    }

    /*
        需求：实现逻辑分页
        为什么要实现逻辑分页?
        真分页相比于假分页不会造成内存溢出，但翻页的数据相比于假分页又慢，所以根据实际情况选择分页方式，如果数据量不大，可以考虑使用假分页使翻页速度加快。
        这是搜索出来的博客，数据量预期将会很小（撑死10来条）

        1. 在Service层（这里）写一个方法，调用Dao层的searchByTitleOrType(SearchBlog searchBlog);
        2. 前端调用service,并为其传递startIndex和pageSize两个参数，便可查出
        3. 为防止数据量过大问题，html要进行校验，不能让用户啥都不填写进行查询（因为这样会查询出整个数据库全部数据，数据量过大）

    */
    @Override
    public List<BlogQuery> searchByTitleOrType(SearchBlog searchBlog, Map<String,Integer> map) {
        //选择全部typeId就是-9999，如果传入了全部的typeid，就将typeid置空
        if (searchBlog.getTypeId() == -9999){
            SearchBlog searchBlogWithoutId = new SearchBlog(searchBlog.getTitle());
            searchBlog = searchBlogWithoutId;
        }

        List<BlogQuery> blogQueries = blogDao.searchByTitleOrType(searchBlog);

        int startIndex = map.get("startIndex");
        int pageSize = map.get("pageSize");

        int lastIndex = startIndex + pageSize;

        //subList都不知道？自己查jdk吧

        if (lastIndex > blogQueries.size())
            lastIndex = blogQueries.size();
        //只要截取出现异常，不管是什么异常造成的，统统返回一个空list。校验逻辑在Controller和html层有，这里不用多bb
        try {
            blogQueries.subList(startIndex, lastIndex);
        } catch (Exception e) {
            //不打印处理日志了，看着不舒服
            //e.printStackTrace();
            blogQueries.clear();
            return blogQueries;
        }


        return blogQueries.subList(startIndex, lastIndex);
    }

    @Override
    public ShowBlog getBlogById(int id) {
        return blogDao.getBlogById(id);
    }

    //只得到blog的标题
    @Override
    public String getBlogTitleById(int id) {
        return blogDao.getBlogTitleById(id);
    }

    //得到搜索出来的博客的总数
    @Override
    public int getSearchByTitleOrTypeCount(SearchBlog searchBlog) {
        if (searchBlog.getTypeId() == -9999){
            SearchBlog searchBlogWithoutId = new SearchBlog(searchBlog.getTitle());
            searchBlog = searchBlogWithoutId;
        }
        List<BlogQuery> blogQueries = blogDao.searchByTitleOrType(searchBlog);
        return blogQueries.size();
    }


    @Override
    public int getBlogsCount() {
        return blogDao.getBlogsCount();
    }

    /*
        理解这里的insertBlog：
        其实是整合了两个Dao的方法
        这两个Dao方法，自己去BlogDao和对应的mapper文件里看
        这里只说明如何使用：
            给定一个Blog，以及Blog选定的好几种分类封装成的集合(List<Type> types)
            然后调用该方法，能将Blog插入到数据库，
            而且能将Blog和Type对应的中间表：blog_type插入到数据库。
    */
    @Override
    public int insertBlog(Blog blog, List<Type> types) {
        blogDao.insertBlogWithoutType(blog);
        for (Type type : types) {
            blogDao.insertBlog_Type(blog, type);
        }
        return 1;
    }

    //删 先删除中间表，后删除Blog 组合两个dao层方法
    @Override
    public int deleteBlog(int id) {
        blogDao.deleteBlog_Type(id);
        blogDao.deleteBlog(id);
        return 1;
    }

    /*
        改 比较复杂
        1. 先修改Blog表，不包含type。  updateBlogWithoutType(Blog blog);
        2. 删除blog_type这个中间表里包括b.id的所有字段 （这样就清理掉了blog表和type表的关系）
        3. 重新调用上面的insertBlog_Type方法，插入blog_type这个中间表
        为什么这么复杂？直接修改中间表不就完事了？非得一删一增才能实现改？
            因为如果直接修改中间表的话，我不会写sql(也可能没有这样的sql)，因为中间表涉及到增删问题，不是单纯的改动！
            举例来说，我现在修改id为1的blog，原本该blog关联了2个type(blog_type有两个字段)
            把它修改关联3个type或关联1个type，这咋办？还拿着id去修改吗？都涉及新增和删除了
     */
    @Override
    public int updateBlog(Blog blog) {
        //第一步
        blogDao.updateBlogWithoutType(blog);
        //第二步
        int id = blog.getId();
        blogDao.deleteBlog_Type(id);
        //第三步
        List<Type> types = blog.getTypes();
        for (Type type : types) {
            blogDao.insertBlog_Type(blog,type);
        }

        return 1;
    }

    @Override
    public void updateBlogViews(int id) {
        blogDao.updateBlogViews(id);
    }
}
