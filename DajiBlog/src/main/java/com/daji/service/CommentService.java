package com.daji.service;

import com.daji.pojo.Comment;
import com.daji.pojo.Message;

import java.util.List;

public interface CommentService {
    //查出所有的评论来（包含父子关系）
    List<Comment> listCommentByBlogId(int blogId);

    //保存评论
    int saveComment(Comment comment);

    //删除评论
    void deleteComment(Comment comment, int id);


    //上面的方法们作用于读文章页面底下的评论区，下面的方法们作用于留言版的留言区！！
    List<Message> listMessage();

    int saveMessage(Message message);
}
