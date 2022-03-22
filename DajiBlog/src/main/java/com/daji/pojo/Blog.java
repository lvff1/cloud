package com.daji.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
    这里面的user统一改成Admin
 */
public class Blog {

    private int id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;    //原创 bi或 转载
    private Integer views;  //浏览次数

    private Integer commentCount;   //评论数

    //是否开启 赞赏 分享 评论
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published; //状态 草稿和发布状态
    private boolean recommend; //推荐
    private Date createTime;
    private Date updateTime;

    /*
        这个typeId字段是必须的嘛? type和admin在李仁密的项目中是一对多关系
        但是在我这里的关系是多对多关系,而且创建了中间表 blog_type
        那么这个字段本来是为一对多关系而生, 现在是多对多关系了, 理论上不需要该字段
        留着他完全是因为怕报错, 如果项目跑通后, 不出现任何问题, 就可以删除该字段了
     */
    private int typeId;
    /*
        typeIds和上面的typeId有本质区别，这个typeIds是负责接收前端传来的参数的，它和数据库没有任何映射关系
        且该typeIds类型为String，上面的typeId类型是int
        后期会对它进行字符串的一些操作
        其传入的数据是这个类型的 ：blog.setTypeIds("1,2,3");  代表了数据库里t_type的三个id
        用到它的地方只有blogs-input.html, 到这里面搜索：typeIds 即可
     */
    private String typeIds;
    private int adminId;
    private String description;

    /*
        private Type type;
        由于修改成了多对多关系, 这个Type字段类型就完全不需要了, 取而代之的是下面的List方法
        在Type实体类中,有一个这样的方法: private List<Blog> blogs; 体现了多对多关系
     */
    private List<Type> types;


    private Admin admin;
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", commentCount=" + commentCount +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", typeId=" + typeId +
                ", typeIds='" + typeIds + '\'' +
                ", adminId=" + adminId +
                ", description='" + description + '\'' +
                ", types=" + types +
                ", admin=" + admin +
                ", comments=" + comments +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    public Blog(int id) {

        this.id = id;
    }
}