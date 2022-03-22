package com.daji.vo;

import com.daji.pojo.Type;

import java.util.Date;
import java.util.List;


public class ShowBlog {

    private int id;
    private String flag;
    private String title;
    private String content;
    private int typeId;
    //多对多
    private List<Type> types;

    private String typeIds;

    private String firstPicture;
    private String description;
    private Integer views;  //浏览次数
    private boolean recommend;
    private boolean published;
    private boolean shareStatement;
    private boolean appreciation;
    private boolean commentabled;
    private Date updateTime;

    public ShowBlog(int id, String flag, String title, String content, int typeId, List<Type> types, String typeIds, String firstPicture, String description, Integer views, boolean recommend, boolean published, boolean shareStatement, boolean appreciation, boolean commentabled, Date updateTime) {
        this.id = id;
        this.flag = flag;
        this.title = title;
        this.content = content;
        this.typeId = typeId;
        this.types = types;
        this.typeIds = typeIds;
        this.firstPicture = firstPicture;
        this.description = description;
        this.views = views;
        this.recommend = recommend;
        this.published = published;
        this.shareStatement = shareStatement;
        this.appreciation = appreciation;
        this.commentabled = commentabled;
        this.updateTime = updateTime;
    }

    public ShowBlog() {
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    @Override
    public String toString() {
        return "ShowBlog{" +
                "id=" + id +
                ", flag='" + flag + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", typeId=" + typeId +
                ", types=" + types +
                ", firstPicture='" + firstPicture + '\'' +
                ", description='" + description + '\'' +
                ", recommend=" + recommend +
                ", published=" + published +
                ", shareStatement=" + shareStatement +
                ", appreciation=" + appreciation +
                ", commentabled=" + commentabled +
                ", updateTime=" + updateTime +
                '}';
    }
}