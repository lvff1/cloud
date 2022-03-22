package com.daji.controller;

import com.daji.pojo.Comment;
import com.daji.pojo.Message;
import com.daji.pojo.User;
import com.daji.vo.ShowBlog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 评论的逻辑较为复杂，所以单独用一个Controller来写
 * 该Controller包括了：
 * 文章底部的评论功能
 * 留言板页面的评论功能
 */

//评论功能，需要shiro权限控制。必须已登录的用户才有资格评论
@Controller
public class CommentController extends BaseController{

    //如果想获取评论头像 -> 从session里拿到登录了的用户的头像
    private String avatar;


    //该方法负责局部刷新read页面的评论区
    @GetMapping("/article/read/comments/{blogId}")
    public String comments(@PathVariable int blogId, Model model) {
        //把该博客旗下的所有评论都给查出来，封装成一集合，并传到前端去
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
        model.addAttribute("comments", comments);
        /*
          主键：局部刷新
          需求：提交表单后，只刷新某页面的一部分内容，不刷新整个页面，要用到ajax
          关联：read.html
        */
        //局部刷新的语法。前面是html页面，后面是th:fragment的值
        return "read::commentList";
    }


    //在read页面，提交评论表单，点击发布按钮走的post方法
    @PostMapping("/article/read/comments")
    public String commentPost(Comment comment, HttpSession session) {
        int blogId = comment.getBlogId();
        User user = (User) session.getAttribute("logineduser"); //从session里拿user的登陆信息
        comment.setAvatar(user.getAvatar());    //得到user的头像
        comment.setAdminComment(false);

        if (user.getEmail().equals("1134107721@qq.com"))     //如果检测到是博主登陆（我这里写的是死代码）
            comment.setAdminComment(true);              //是Admin评论，设置为true


        commentService.saveComment(comment);
        //redirect到上面那个局部刷新的方法
        return "redirect:/article/read/comments/" + blogId;
    }

    /*
        上面的方法们作用于读文章页面底下的评论区，下面的方法们作用于留言版的留言区！！
        注释全删了，因为逻辑和上面完全完全一样，只是变量名变了，和去掉了BlogId,以及接口的路径也变了
     */

    //该方法负责局部刷新read页面的评论区
    @GetMapping("/message/load")
    public String message(Model model) {
        //把该博客旗下的所有评论都给查出来，封装成一集合，并传到前端去
        List<Message> messages = commentService.listMessage();
        for (Message message : messages) {
            System.out.println(message);
        }
        model.addAttribute("messages", messages);

        return "message::messageList";
    }

    //在read页面，提交评论表单，点击发布按钮走的post方法
    @PostMapping("/message/load")
    public String messagePost(Message message, HttpSession session) {

        User user = (User) session.getAttribute("logineduser"); //从session里拿user的登陆信息
        message.setAvatar(user.getAvatar());    //得到user的头像
        message.setAdminMessage(false);

        if (user.getEmail().equals("1134107721@qq.com"))     //如果检测到是博主登陆（我这里写的是死代码）
            message.setAdminMessage(true);              //是Admin评论，设置为true

        commentService.saveMessage(message);
        //redirect到上面那个局部刷新的方法
        return "redirect:/message";
    }


}
