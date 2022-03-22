package com.daji.vo;

import com.daji.pojo.Type;


import java.util.Date;
import java.util.List;

/*需要在mybatis配置文件中配置别名, 别忘了*/
public class BlogQuery {

    private int id;
    private String title;
    private Date updateTime;
    private Boolean recommend;
    private Boolean published;
    private int typeId;
    private List<Type> types;

    private String thymeleaf_typename;

    //以下5个是article需要的额外的东西。上面是后台需要的东西
    private String flag;
    private String firstPicture;
    private String description;
    private Integer views;
    private Integer commentCount;

    public BlogQuery(int id, String title, Date updateTime, Boolean recommend, Boolean published, int typeId, List<Type> types, String flag, String firstPicture, String description, Integer views, Integer commentCount) {
        this.id = id;
        this.title = title;
        this.updateTime = updateTime;
        this.recommend = recommend;
        this.published = published;
        this.typeId = typeId;
        this.types = types;
        this.thymeleaf_typename = thymeleaf_typename;
        this.flag = flag;
        this.firstPicture = firstPicture;
        this.description = description;
        this.views = views;
        this.commentCount = commentCount;
    }

    public BlogQuery() {
    }

    @Override
    public String toString() {
        return "BlogQuery{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", recommend=" + recommend +
                ", published=" + published +
                ", typeId=" + typeId +
                ", types=" + types +
                ", thymeleaf_typename='" + thymeleaf_typename + '\'' +
                ", flag='" + flag + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", commentCount=" + commentCount +
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public String getThymeleaf_typename() {
        return thymeleaf_typename;
    }

    public void setThymeleaf_typename(String thymeleaf_typename) {
        this.thymeleaf_typename = thymeleaf_typename;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}