package com.daji.pojo;

import java.util.Date;

public class User {
    private int uid;
    private String email;
    private String pwd;
    private String nickname;    //昵称
    private int status;     //账号状态(激活与否)
    private int emailcode;      //用户收到的邮箱激活码
    private String role;   //权限控制（shiro要用到）
    private String avatar;

    //下面两个属性是myblog的属性, 暂时还没有添加get, set tostring contruster方法
    private Integer type;   //用户的类型
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间


    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                ", emailcode=" + emailcode +
                '}';
    }

    public User(int uid, String email, String pwd, String nickname, int status, int emailcode) {
        this.uid = uid;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.status = status;
        this.emailcode = emailcode;
    }

    public User() {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmailcode() {
        return emailcode;
    }

    public void setEmailcode(int emailcode) {
        this.emailcode = emailcode;
    }
}
