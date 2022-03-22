package com.daji.vo;


public class SearchBlog {

    private String title;
    private int typeId;

    public SearchBlog() {
    }


    public SearchBlog(String title, int typeId) {
        this.title = title;
        this.typeId = typeId;
    }
    //下面两个拷贝构造函数，是为了应对用户搜索只输入title或是只输入类型的情况
    public SearchBlog(String title) {
        this.title = title;
    }

    public SearchBlog(int typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    @Override
    public String toString() {
        return "SearchBlog{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}