package com.daji.controller;


import com.daji.pojo.Admin;
import com.daji.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/*
    定义了管理员的一些行为.
    管理员对应的实体类是 t_user
 */
@Controller
public class AdminController extends BaseController{

    @RequestMapping("/admin/dashboard")
    public String toDashboard(){
        return "admin/dashboard";
    }




    @RequestMapping("/adminlogin")
    public String adminLogin(@RequestParam("adminname")String adminname, @RequestParam("password")String password, Model model, HttpSession session){
        //这里为啥登陆的实际用户是user类型而不是admin类型？ 因为架构失误，不适用admin了（admin类型其实已经被架空了）
        User user = userService.getUserByEmail(adminname);

        //因为架构失误不得不出现的狗屎代码
        Admin admin = adminService.findByAdminnameAndPassword(adminname, password);
        if (admin == null){
            System.out.println("没有这个管理员");
            return "admin/adminlogin";
        }


        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(adminname,password));
            System.out.println(user.getNickname() + ",欢迎管理员登陆成功!");
            session.setAttribute("loginedadmin", user);
            return "admin/dashboard";
        } catch (Exception e) {
            System.out.println("用户名或密码错误");
            model.addAttribute("adminloginerror", "用户名或密码错误");
            return "admin/adminlogin";
        }


        //下面被注释掉的代码别删，是架构失误前的代码
        /*Admin recentadmin = adminService.findByAdminnameAndPassword(adminname, password);
        if (recentadmin != null){
            System.out.println("登陆成功");
            session.setAttribute("loginedadmin", recentadmin);
            return "admin/dashboard";
        }
        else{
            System.out.println("用户名或密码错误");
            model.addAttribute("adminloginerror", "用户名或密码错误");
            return "admin/adminlogin";
        }*/
    }

    @RequestMapping("/admin/adminlogout")
    public String adminLogout(HttpSession session){

        session.removeAttribute("loginedadmin");
        //退出登录的同时，清除shiro的权限缓存
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/toAdminLogin";
    }


}
