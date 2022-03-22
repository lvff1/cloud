package com.daji.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.daji.pojo.Admin;
import com.daji.pojo.Blog;
import com.daji.pojo.Type;
import com.daji.utils.FileUploadUtils;
import com.daji.vo.BlogQuery;
import com.daji.vo.SearchBlog;
import com.daji.vo.ShowBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController extends BaseController {

    @Autowired
    @Qualifier("redisTemplate") //这里的意思是指定我们自己配置的RedisConfig.java 里面的那个redisTemplate。而不是用系统默认的
    private RedisTemplate redisTemplate;


    /*
        该方法是一开始点击博客管理会去到的地方，
        作用是：点击文章管理，前端会默认显示所有的博客

        下面还有一个PostMapping("/blogs")
        作用是提交（新增或修改）之后，走的方法，也是redirect到admin/blogs页面
        这里的为什么用get？因为只有点击博文管理（文章管理）才会走这个方法
        此处体现了Restful风格
    */
    @GetMapping("/blogs")
    public String toBlogAdminPage(Model model,RedirectAttributes attributes, @RequestParam(defaultValue = "0", value = "startIndex") int startIndex) {
        //先传一个名为types参数给前端过去，这样可以实现下拉分类列表，会从数据库中获取出所有的分类信息。
        List<Type> type1 = typeService.queryAllTypes();
        model.addAttribute("types", type1);

        //指定每页容量
        int pageSize = 8;
        HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("startIndex", startIndex);
        pageMap.put("pageSize", pageSize);
        //根据前端传入的startIndex和指定的每页容量，分页查询
        List<BlogQuery> blogQueries = blogService.queryAllBlogByLimit(pageMap);
        //从这里的代码往下，就是为了解决一个坑爹情况，该坑爹情况由于本人前端技术有限，只能通过写这一坨屎来解决
        String s = "";
        for (BlogQuery blogQuery : blogQueries) {
            List<Type> types = blogQuery.getTypes();
            for (Type type : types) {
                s = s + type.getName() + "，";
            }
            s = s.substring(0,s.length() - 1);  //去掉最后一个逗号
            //这里，blogQuery类的setThymeleaf_typename字段，看名字(相当不用心)就知道是为了在这里处理而专门创建的变量，纯粹为了前端回显
            blogQuery.setThymeleaf_typename(s);
            s = "";
        }
        //屎代码结束 写这坨屎的根本原因是 我不会thymeleaf的嵌套tr中嵌套th:each。

        if (blogQueries.size() == 0) {
            System.out.println("查不出来了!到最后一页了。后期给用户回显一个提示： 到最后一页了哦");
            attributes.addFlashAttribute("negativeMessage", "别点啦，已经是最后一页了");
            return "redirect:/admin/blogs?startIndex=" + (startIndex - pageSize);
        }
        //将查出来的分页数据回显给前端
        model.addAttribute("blogQueries", blogQueries);

        //下面两个东西，是为了辅助前端校验上一页和下一页合法性，比如是否越界等
        model.addAttribute("recentStartIndex", startIndex);
        model.addAttribute("pageSize", pageSize);
        //下面的东西是为了回显前端 共几页 共多少篇博客
        int totalBlogs = blogService.getBlogsCount();
        model.addAttribute("totalBlogs", totalBlogs);  //共多少篇博客
        int totalPages = (totalBlogs % pageSize == 0 ? totalBlogs / pageSize : totalBlogs / pageSize + 1);    //通过共有多少篇博客和页码容量，算出总页数。并不难
        model.addAttribute("totalPages", totalPages);    //共几页

        return "admin/blogs";
    }
    /*
        搜索博客
    */
    @RequestMapping("/blogs/search")
    public String toBlogSearchPage(Model model, RedirectAttributes attributes, @RequestParam(defaultValue = "0", value = "startIndex") int startIndex, SearchBlog searchBlog) {

        //打印下获取成功与否
        System.out.println(searchBlog.getTitle());
        System.out.println(searchBlog.getTypeId());
        System.out.println(searchBlog);
        //指定每页容量
        int pageSize = 8;
        HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("startIndex", startIndex);
        pageMap.put("pageSize", pageSize);
        //根据前端传入的startIndex和指定的每页容量，分页查询
        List<BlogQuery> blogQueries = blogService.searchByTitleOrType(searchBlog, pageMap);


        //查无数据。和后面的查到最后一页有区别
        if (blogQueries.size() == 0 && startIndex == 0){
            System.out.println("查无数据，请换个搜索关键词");
            model.addAttribute("negativeMessage", "查无数据，请换个搜索关键词");
            return "forward:/admin/blogs";      //为啥用forward？我希望能把msg带过去
        }
        if (blogQueries.size() == 0) {
            System.out.println("查不出来了!到最后一页了。后期给用户回显一个提示： 到最后一页了哦");
            /*
                重要！ addFlashAttribute的妙用！
            */
            attributes.addFlashAttribute("negativeMessage", "别点啦，已经是最后一页了");
            //这里必须用redirect，但是我还想向前端传数据，那怎么办？？ 看上方的addFlashAttribute。实际上用session就可以做到

            /*
                下面出现一个问题：
                return一个url时，如果里面有中文，会乱码
                经过网上查询解决方案，发现用这个方法就不会出错了：URLEncoder.encode("要传入的中文","utf-8")
                但是这个方法会报红，只需要trycatch一下就好了。
             */
            try {
                return "redirect:/admin/blogs/search?startIndex=" + (startIndex - pageSize) + "&title=" + URLEncoder.encode(searchBlog.getTitle(),"utf-8") + "&typeId=" + searchBlog.getTypeId();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        //将查出来的分页数据回显给前端
        model.addAttribute("blogQueries", blogQueries);

        //下面两个东西，是为了辅助前端校验上一页和下一页合法性，比如是否越界等
        model.addAttribute("recentStartIndex", startIndex);
        model.addAttribute("pageSize", pageSize);
        //下面的东西是为了回显前端 共几页 共多少篇博客
        int totalBlogs = blogService.getSearchByTitleOrTypeCount(searchBlog);
        model.addAttribute("totalBlogs", totalBlogs);  //共多少篇博客
        int totalPages = (totalBlogs % pageSize == 0 ? totalBlogs / pageSize : totalBlogs / pageSize + 1);    //通过共有多少篇博客和页码容量，算出总页数。并不难
        model.addAttribute("totalPages", totalPages);    //共几页

        //上一页和下一页，发送这个searchBlog
        model.addAttribute("searchBlog", searchBlog);

        return "admin/blogs-search";
    }

    //跳转博客新增页面
    @GetMapping("/blogs/input")
    public String toInputPage(Model model) {
        model.addAttribute("types",typeService.queryAllTypes());
        /*
            因为是新增，传一个空的blog进去
            必须传这个东西，否则无法和修改共用一个thymeleaf页面
         */
        model.addAttribute("blog", new Blog());




        return "admin/blogs-input";
    }
    /*
        博客新增页面的 提交按钮会走的方法
        最终会redirect到全部博客页面
        前面有一个GetMapping的同名方法。
        这里是Restful风格
    */
    @PostMapping("/blogs")
    public String input(Blog blog, RedirectAttributes attributes, HttpSession session, @RequestParam("uploadPicture")MultipartFile uploadPicture){
        //从session里拿Admin   , 再通过admin拿adminId
        /*Admin loginedadmin = (Admin) session.getAttribute("loginedadmin");
        blog.setAdminId(loginedadmin.getId());*/

        //为啥注释掉上面的三行代码？注释掉上面三行代码会造成什么后果？ 因为从session里面已经拿不到admin类型了，
        // 只能拿到user类型（架构失误导致admin类型被架空）。目前只有大吉一个管理员，那么所有文章都出自1号管理员大吉，我就写死了，写为1
        blog.setAdminId(1);

        System.out.println(blog);

        //需求： 判断作者有没有上传首图，如果作者没上传，那么就给一个默认的首图地址; 如果作者上传了，就将该首图文件路径转换成对应数据库的URL
        String firstPictureURL = "../../static/images/user-avatar/胡哥的大头照.jpg";
        if (uploadPicture.getSize() != 0){
            firstPictureURL = FileUploadUtils.convertFileToURL(uploadPicture);
        }


        //设置blog的type们
        List<Type> types = typeService.listType(blog.getTypeIds());


        //下面手动设置blog里面的一些前端没能获取到的属性
        if(blog.getCreateTime() == null)
            blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        //设置首图
        blog.setFirstPicture(firstPictureURL);

        //插入Blog，需要传俩参数，blog和对应的types
        int b = blogService.insertBlog(blog, types);
        //将刚刚插入到MySQL中的blog同步到Redis中
        //redisTemplate.opsForValue().set("blog:" + blog.getId(), blog);

        if(b != 1){
            attributes.addFlashAttribute("message", "新增失败");
            System.out.println("新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
            System.out.println("新增成功");
        }
        return "redirect:/admin/blogs";
    }
    //    跳转修改文章页面
    @GetMapping("/blogs/{id}/input")
    public String toEditPage(@PathVariable int id, Model model) {
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> types = blogById.getTypes();
        //传入一个types，能解析成字符串形如"1,2,3"
        String s = typeService.unListType(types);
        blogById.setTypeIds(s);

        List<Type> allType = typeService.queryAllTypes();
        model.addAttribute("blog", blogById);
        model.addAttribute("types", allType);
        return "admin/blogs-input";
    }
    //    提交修改文章表单
    @PostMapping("/blogs/{id}")
    public String editPost(Blog blog, RedirectAttributes attributes,@RequestParam("uploadPicture")MultipartFile uploadPicture) {

        //需求： 判断作者有没有上传首图，如果作者没上传，那么就使用原先的首图地址(因为这是修改); 如果作者上传了，就将新的首图文件路径转换成对应数据库的URL
        //在新增文章的方法中，也有一个类似的逻辑。
        ShowBlog blogById = blogService.getBlogById(blog.getId());
        String firstPictureURL = blogById.getFirstPicture();    //如果作者没上传，就使用原先的首图地址

        //假如上传的文件大小为0，就可以判空（作者未上传）
        if (uploadPicture.getSize() != 0){
            firstPictureURL = FileUploadUtils.convertFileToURL(uploadPicture);   //假如作者上传了新的首图，那么就将新的首图文件路径转换成对应数据库的URL
        }



        //设置blog的type们
        List<Type> types = typeService.listType(blog.getTypeIds());
        blog.setTypes(types);
        //设置修改时间
        blog.setUpdateTime(new Date());
        //设置首图
        blog.setFirstPicture(firstPictureURL);

        int b = blogService.updateBlog(blog);

        if(b != 1){
            attributes.addFlashAttribute("message", "修改失败");
            System.out.println("修改失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
            System.out.println("修改成功");
        }

        return "redirect:/admin/blogs";
    }

    //    删除文章
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }



}
