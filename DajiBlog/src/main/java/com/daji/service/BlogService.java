package com.daji.service;

import com.daji.pojo.Blog;
import com.daji.pojo.Type;
import com.daji.vo.BlogQuery;
import com.daji.vo.SearchBlog;
import com.daji.vo.ShowBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    List<BlogQuery> queryAllBlog();

    ShowBlog getAndConvert(int id);

    List<BlogQuery> queryAllBlogOrderByViews();

    List<BlogQuery> queryAllRecommendBlog();

    List<BlogQuery> queryAllBlogByLimit(Map<String, Integer> map);
    //所有带 _Redis的方法，都是代替了上面的方法，和上面的方法功能一模一样，但是是从redis中取数据
    List<BlogQuery> queryAllBlogByLimit_Redis(Map<String, Integer> map);

    List<BlogQuery> searchByTitleOrType(SearchBlog searchBlog, Map<String, Integer> map);

    ShowBlog getBlogById(int id);

    String getBlogTitleById(int id);

    int getSearchByTitleOrTypeCount(SearchBlog searchBlog);

    int getBlogsCount();

    int insertBlog(Blog blog, List<Type> types);

    int deleteBlog(int id);

    int updateBlog(Blog blog);

    void updateBlogViews(int id);


}
