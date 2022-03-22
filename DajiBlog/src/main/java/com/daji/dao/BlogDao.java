package com.daji.dao;

import com.daji.pojo.Blog;
import com.daji.pojo.Type;
import com.daji.vo.BlogQuery;
import com.daji.vo.SearchBlog;
import com.daji.vo.ShowBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description: 博客持久层接口
 * @Author: ONESTAR
 * @Date: Created in 23:37 2020/3/30
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Mapper
@Repository
public interface BlogDao {

    //先从需要的功能开始

    //查询所有博客
    List<BlogQuery> queryAllBlog();

    //查询所有博客（根据浏览量排序，只查询前8个。因为热门文章只有前8篇）
    List<BlogQuery> queryAllBlogOrderByViews();

    //查询所有被推荐的博客（置顶模块） 按照update_time排序
    List<BlogQuery> queryAllRecommendBlog();

    /*
        分页查询所有博客（查询所有博客分页展示）。形参是limit的参数
        为什么 要这么做？不是有pagehelper吗？
        因为PageHelper不支持多对多
        不得已，才出此下策，手动分页

        该方法考虑到了置顶(recommend)优先!
        至于怎么做到的，看我的csdn博客：SQL语句 ORDER BY 多条件排序优先级（嵌套if语句） https://blog.csdn.net/weixin_44757863/article/details/110839219
        该方法首先根据updatetime排序，然后根据recommend排序。还能分页！
        神奇的sql语句
     */
    List<BlogQuery> queryAllBlogByLimit(Map<String, Integer> map);

    List<BlogQuery> searchByTitleOrType(SearchBlog searchBlog);

    //sql语句就一条，非常简单 SELECT COUNT(*) FROM t_blog 就是得到一共有多少条博客
    int getBlogsCount();

    ShowBlog getBlogById(int id);

    //只得到博客的标题
    String getBlogTitleById(int id);

    //增 因为是多表的缘故，被分成了两个方法，但是他们两个将在Service层合二为一
    int insertBlogWithoutType(Blog blog);
    int insertBlog_Type(@Param("blog") Blog blog, @Param("type") Type type);


    //删 先删除中间表，后删除Blog
    int deleteBlog_Type(@Param("bid") int bid);
    int deleteBlog(@Param("id") int id);

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
    int updateBlogWithoutType(Blog blog);

    //更新博客浏览量 每调用一次，浏览量就自增1
    void updateBlogViews(int id);



    //下面是暂时不需要的
    /*ShowBlog getBlogById(Long id);

    List<Blog> getAllBlog();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);



    List<FirstPageBlog> getFirstPageBlog();

    List<RecommendBlog> getAllRecommendBlog();

//    List<FirstPageBlog> getNewBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    int updateViews(Long id);

//    根据博客id查询出评论数量
    int getCommentCountById(Long id);

    List<FirstPageBlog> getByTypeId(Long typeId);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();*/
}
