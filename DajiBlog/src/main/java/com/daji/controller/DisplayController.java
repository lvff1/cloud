package com.daji.controller;

import com.daji.pojo.Type;
import com.daji.vo.BlogQuery;
import com.daji.vo.SearchBlog;
import com.daji.vo.ShowBlog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * 前端展示页的Controller
 * 包括：博客页面，留言页面，友链页面，日记页面
 * 以及他们的子孙页面全部行为
 */
@Controller
public class DisplayController extends BaseController{
    /**
     * 博客页面及子孙页面。统一以 /article开头
     */
    @GetMapping("/article")
    public String toArticle(Model model, RedirectAttributes attributes, @RequestParam(defaultValue = "0", value = "startIndex") int startIndex) {
        //指定每页容量
        int pageSize = 8;
        HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("startIndex", startIndex);
        pageMap.put("pageSize", pageSize);
        //根据前端传入的startIndex和指定的每页容量，分页查询
        List<BlogQuery> blogQueries = blogService.queryAllBlogByLimit(pageMap);


        if (blogQueries.size() == 0) {
            System.out.println("查不出来了!到最后一页了。后期给用户回显一个提示： 到最后一页了哦");
            attributes.addFlashAttribute("negativeMessage", "已经是最后一页了哦");
            return "redirect:/article?startIndex=" + (startIndex - pageSize);
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

        //分类列表传递到前端
        List<Type> typeList = typeService.queryAllTypes();
        model.addAttribute("types", typeList);

        //热门文章列表传递给前端(浏览量最高的8篇热文)
        List<BlogQuery> hotArticle = blogService.queryAllBlogOrderByViews();
        model.addAttribute("hotArticle", hotArticle);

        //置顶推荐模块
        List<BlogQuery> recommendBlogs = blogService.queryAllRecommendBlog();
        model.addAttribute("recommendBlogs",recommendBlogs);

        return "article";
    }

    //浏览某分类下的全部博客(分类浏览)
    @GetMapping("/article/type/{id}")   //这里的id和下面的路径变量id冲突吗？不冲突
    public String classifiedBrowsing(@PathVariable int id, Model model, @RequestParam(defaultValue = "0", value = "startIndex") int startIndex, RedirectAttributes attributes){
        //这个typeId,出现在article-type.html里面的上一页和下一页。 到article-type.html里看就懂了
        // 为了解决这里的问题，我还写了一篇博客 ： https://blog.csdn.net/weixin_44757863/article/details/110797979
        model.addAttribute("typeId",id);

        //指定每页容量
        int pageSize = 8;
        HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("startIndex", startIndex);
        pageMap.put("pageSize", pageSize);
        //根据前端传入的startIndex和指定的每页容量，分页查询
        List<BlogQuery> blogQueries = blogService.searchByTitleOrType(new SearchBlog(id), pageMap);

        if (blogQueries.size() == 0) {
            System.out.println("查不出来了!到最后一页了。后期给用户回显一个提示： 到最后一页了哦");
            attributes.addFlashAttribute("negativeMessage", "已经是最后一页了哦");
            //attributes.addFlashAttribute("negativeMessage", "别点啦，已经是最后一页了");
            return "redirect:/article/type/"+id+"?startIndex=" + (startIndex - pageSize);
        }

        //将查出来的分页数据回显给前端
        model.addAttribute("blogQueries", blogQueries);

        //下面两个东西，是为了辅助前端校验上一页和下一页合法性，比如是否越界等
        model.addAttribute("recentStartIndex", startIndex);
        model.addAttribute("pageSize", pageSize);
        //下面的东西是为了回显前端 共几页 共多少篇博客
        int totalBlogs = blogService.getSearchByTitleOrTypeCount(new SearchBlog(id));
        model.addAttribute("totalBlogs", totalBlogs);  //共多少篇博客
        int totalPages = (totalBlogs % pageSize == 0 ? totalBlogs / pageSize : totalBlogs / pageSize + 1);    //通过共有多少篇博客和页码容量，算出总页数。并不难
        model.addAttribute("totalPages", totalPages);    //共几页

        //分类列表传递到前端
        List<Type> typeList = typeService.queryAllTypes();
        model.addAttribute("types", typeList);

        //热门文章列表传递给前端(浏览量最高的8篇热文)
        List<BlogQuery> hotArticle = blogService.queryAllBlogOrderByViews();
        model.addAttribute("hotArticle", hotArticle);

        //置顶推荐模块
        List<BlogQuery> recommendBlogs = blogService.queryAllRecommendBlog();
        model.addAttribute("recommendBlogs",recommendBlogs);

        return "article-type";
    }
    /*
        搜索博客
    */
    @RequestMapping("/article/search")
    public String toBlogSearchPage(Model model, RedirectAttributes attributes, @RequestParam(defaultValue = "0", value = "startIndex") int startIndex, SearchBlog searchBlog) {

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
            return "forward:/article";      //为啥用forward？我希望能把msg带过去
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
                return "redirect:/article/search?startIndex=" + (startIndex - pageSize) + "&title=" + URLEncoder.encode(searchBlog.getTitle(),"utf-8");
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

        return "article-search";
    }



    //阅读某博客
    @GetMapping("/article/read/{id}")
    public String toRead(@PathVariable int id, Model model){


        //ShowBlog blogById = blogService.getBlogById(id);

        //Markdown格式化 在这里应用。调用了一个service层的方法
        ShowBlog blogById = blogService.getAndConvert(id);
        //浏览量++
        blogService.updateBlogViews(blogById.getId());
        model.addAttribute("blog", blogById);

        /*
            随机生成 延伸阅读 模块的文章
            由于id是不连续的（中间有缺号现象），所以不保证能查寻到有效id
            下面的死循环，可以保证查询到有效id（查不到有效的就一直循环下去了）
         */
        String blogTitle;
        int RandomId;
        while (true){
            // 公式： (数据类型)(最小值+Math.random()*(最大值-最小值+1)) 下面意思是1到50的随机数 后期文章多了可以调大一点
            RandomId = (int) (1 + Math.random() * (50 - 1 + 1));
            blogTitle = blogService.getBlogTitleById(RandomId);
            if (blogTitle != null)
                break;
        }
        //将随机到的 文章标题 和 id 回传给前端
        // 共有3篇文章，所以传三个标题（其实只有第一个标题是随机的，另外两个标题是它相邻的两个id查出来的标题）
        model.addAttribute("blogTitle", blogTitle);
        model.addAttribute("blogTitle2", blogService.getBlogTitleById(RandomId+1));
        model.addAttribute("blogTitle3", blogService.getBlogTitleById(RandomId+2));
        model.addAttribute("RandomId", RandomId);

        return "read";
    }


    /**
     * 留言页面及子孙页面。统一以 /message开头
     */
    @GetMapping("/message")
    public String toMessage(){
        return "message";
    }


    /**
     * 日记页面及子孙页面。统一以 /diary开头    可以记录该博客开发日志，架构图等等
     */
    @GetMapping("/diary")
    public String toDiary(){
        return "diary";
    }


    /**
     * 友链页面及子孙页面。统一以 /link开头
     */
    @GetMapping("/link")
    public String toLink(){
        return "link";
    }


    /**
     * 关于我页面及子孙页面。统一以 /about开头
     */
    @GetMapping("/about")
    public String toAbout(){
        return "about";
    }
}
