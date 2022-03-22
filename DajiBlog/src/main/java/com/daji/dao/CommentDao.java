package com.daji.dao;

import com.daji.pojo.Comment;
import com.daji.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    //查询父级回复(数据库中parent_comment_id为-1的字段是父节点)
    List<Comment> findByBlogIdParentIdNull(@Param("blogId") int blogId);

    //查询非父级回复
    List<Comment> findByBlogIdParentIdNotNull(@Param("blogId") int blogId, @Param("id") int id);

    //查询父级对象
//    Comment findByParentCommentId(int parentCommentId);

    //添加一个评论
    int saveComment(Comment comment);

    //删除评论
    void deleteComment(int id);

    //上面的方法们作用于读文章页面底下的评论区，下面的方法们作用于留言版的留言区
    List<Message> findMessageByParentId(@Param("parentId") int parentId);

    int saveMessage(Message message);
}
