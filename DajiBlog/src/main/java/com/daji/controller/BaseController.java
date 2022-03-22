package com.daji.controller;

import com.daji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
* Controller的基类, 所有的Controller都要继承这个Controller
* */
public class BaseController {

    @Autowired
    public UserService userService;
    @Autowired
    public AdminService adminService;
    @Autowired
    public TypeService typeService;
    @Autowired
    public BlogService blogService;
    @Autowired
    public CommentService commentService;

    /*public static UserService userService;
    public static AdminService adminService;
    public static TypeService typeService;
    public static BlogService blogService;
    public static CommentService commentService;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (UserService) context.getBean("UserServiceImpl");
        adminService = (AdminService) context.getBean("AdminServiceImpl");
        typeService = (TypeService) context.getBean("TypeServiceImpl");
        blogService = (BlogService) context.getBean("BlogServiceImpl");
        commentService = (CommentService) context.getBean("CommentServiceImpl");
    }*/
}
