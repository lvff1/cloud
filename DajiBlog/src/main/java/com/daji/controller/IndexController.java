package com.daji.controller;

import com.daji.vo.BlogQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


//只有一个作用,当用户访问localhost:8080或是输入localhost:8080/index.html时 会直接跳到index.html里
@Controller
public class IndexController extends BaseController{
    /*@RequestMapping({"/", "/index.html"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }*/
    @RequestMapping({"/", "/index.html", "/index"})
    public String index(Model model) {


        //置顶推荐前三篇文章
        List<BlogQuery> thirdRecommend = new ArrayList<>();  //只向前端展示前三篇置顶文章

        List<BlogQuery> recommendBlogs = blogService.queryAllRecommendBlog();
        for (int i = 0; i < 3; i++) {
            BlogQuery blogQuery = recommendBlogs.get(i);
            thirdRecommend.add(blogQuery);
        }
        model.addAttribute("thirdRecommend",thirdRecommend);

        return "index";
    }
}