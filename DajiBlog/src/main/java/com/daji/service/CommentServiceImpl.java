package com.daji.service;

import com.daji.dao.BlogDao;
import com.daji.dao.CommentDao;
import com.daji.pojo.Comment;
import com.daji.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    //这里是自动注入, 理论上就不用配置Bean了
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    //存放迭代找出的所有子，孙，重孙，曾孙代的集合（一视同仁）
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(int blogId) {
        //查询出父节点 数据库中parent_comment_id为-1的字段是父节点
        List<Comment> comments = commentDao.findByBlogIdParentIdNull(blogId);
        for(Comment comment : comments){
            int id = comment.getId();
            String parentNickname1 = comment.getNickname();
            //查出父结点的子评论（该方法并不能查出父节点的孙，曾孙，重孙等评论！）
            List<Comment> childComments = commentDao.findByBlogIdParentIdNotNull(blogId,id);
            //查询出子评论
            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>(); //办完事后清理一下该集合，方便下次使用
        }
        return comments;
    }
    /*
        该方法，是将所有除了父评论(爹级)的所有评论(不管你是子，孙，曾孙，重孙)找出来
        并且一视同仁，全部视作父评论的子评论
        也就是说不管它们在数据库里面有多复杂的关系（子，孙，曾孙，重孙这种连续几级关系）
        一律一视同仁，看作子级。只要你上面有父级评论(数据库中parent_comment_id不为-1)，那么一视同仁。
        因为在前端页面中，只有两层关系：父级评论和子级评论

        该方法和下一个方法都是private的。因为只有这个类有权调用它们，出了这个类，为了安全起见，不能调用它们
        所以使用private。（该方法和下一个方法都是为listCommentByBlogId服务的）
     */
    private void combineChildren(int blogId, List<Comment> childComments, String parentNickname1) {
        //判断是否有子评论
        if(childComments.size() > 0){
            // 遍历出每一个子评论来
            for(Comment childComment : childComments){
                //将子评论的昵称单独拿出来，以便下次递归传进去
                String parentNickname = childComment.getNickname();
                //将父评论的nickname属性赋值给子评论的 ParentNickname属性
                childComment.setParentNickname(parentNickname1);
                //一视同仁添加进子集合中
                tempReplys.add(childComment);
                int childId = childComment.getId();
                //查询出子二级，子三级……所有评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(int blogId, int childId, String parentNickname1) {
        //根据子级评论的id找到孙级评论。其实和之前父查子的那个方法一样。子级和孙级也是父子关系啊
        List<Comment> replayComments = commentDao.findByBlogIdParentIdNotNull(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                int replayId = replayComment.getId();
                tempReplys.add(replayComment);
                /*
                    recursively意为递归调用
                    啥意思呢？递归调用本身，直到找全了整个评论链条下所有的评论（子二级，子三级……以此类推）
                 */
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    //    新增评论
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int comments = commentDao.saveComment(comment);
        //        文章评论计数
        //blogDao.getCommentCountById(comment.getBlogId());
        return comments;
    }

    //    删除评论
    @Override
    public void deleteComment(Comment comment,int id) {
        commentDao.deleteComment(id);
        //blogDao.getCommentCountById(comment.getBlogId());
    }

    /*
        上面的方法们作用于读文章页面底下的评论区，下面的方法们作用于留言版的留言区！！
        注释全删了，因为逻辑和上面完全完全一样，只是变量名变了，和去掉了BlogId
     */
    private List<Message> tempReplys_message = new ArrayList<>();

    @Override
    public List<Message> listMessage() {
        List<Message> messages = commentDao.findMessageByParentId(-1);
        for (Message message : messages) {
            int id = message.getId();
            String parentNickname1 = message.getNickname();
            List<Message> childMessages = commentDao.findMessageByParentId(id);
            combineChildren(childMessages, parentNickname1);
            message.setReplyMessages(tempReplys_message);
            tempReplys_message = new ArrayList<>();
        }
        return messages;
    }

    private void combineChildren(List<Message> childMessages, String parentNickname1) {
        //判断是否有子评论
        if(childMessages.size() > 0){
            // 遍历出每一个子评论来
            for(Message childMessage : childMessages){
                //将子评论的昵称单独拿出来，以便下次递归传进去
                String parentNickname = childMessage.getNickname();
                //将父评论的nickname属性赋值给子评论的 ParentNickname属性
                childMessage.setParentNickname(parentNickname1);
                //一视同仁添加进子集合中
                tempReplys_message.add(childMessage);
                int childId = childMessage.getId();
                //查询出子二级，子三级……所有评论
                recursively(childId, parentNickname);
            }
        }
    }

    private void recursively(int childId, String parentNickname1) {
        //根据子级评论的id找到孙级评论。其实和之前父查子的那个方法一样。子级和孙级也是父子关系啊
        List<Message> replayMessages = commentDao.findMessageByParentId(childId);

        if(replayMessages.size() > 0){
            for(Message replayMessage : replayMessages){
                String parentNickname = replayMessage.getNickname();
                replayMessage.setParentNickname(parentNickname1);
                int replayId = replayMessage.getId();
                tempReplys_message.add(replayMessage);
                recursively(replayId,parentNickname);
            }
        }
    }

    //    新增评论
    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());
        int messages = commentDao.saveMessage(message);
        return messages;
    }
}
