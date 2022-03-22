package com.daji.controller;

import com.daji.pojo.User;
import com.daji.utils.MailUtils;
import com.daji.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController extends BaseController{

    /*
        判断登陆是否成功的对象,现在没连数据库.
        所以只需要用户名不为空, 且符合电子邮件的格式,密码是固定的123, 就让他登录成功.

        登陆成功后返回index.html, 然后index.html给回显一个 欢迎您:(用户名)信息
        而且这个登录成功的index.html不显示登录按钮, 取而代之的是 注销按钮
    */

    @RequestMapping("/login")   //这里的@RequestParam("username")是从前端(index.html)里面接收传来的参数(字段名为name的属性, 会传过来.
    public String login(@RequestParam("email") String email, @RequestParam("pwd") String pwd, Model model,HttpSession session){
        User user = userService.getUserByEmail(email);
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(email,pwd));
            System.out.println(user.getNickname() + ",欢迎您登陆成功!");
            session.setAttribute("logineduser", user);
            return "redirect:/article";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("loginerror","用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
            model.addAttribute("loginerror","密码错误");
        }
        return "login";


        /*User user = userService.getUserByEmail(email);
        //如果该用户存在 而且 密码正确 而且 被激活了 那么就让他登录
        if (user != null && user.getPwd().equals(pwd) && user.getStatus()==1){
            //回显欢迎信息
            System.out.println(user.getNickname() + ",欢迎您登陆成功!");
            *//*
                添加一个属性, 就是已经登录了的对象.
                这里非常重要,这就是model.addAttribute传对象的操作. 在index.html里面有对它的接收
             *//*
            model.addAttribute("logineduser", user);

            //这里最好是登陆成功返回一个新的页面 不过我没写 暂时还是index  到新的页面去 就可以注销了,而且登陆和注册按钮都没了
            return "index";
        }else{
            if (user == null){
                System.out.println("用户名错误");
                model.addAttribute("loginerror","用户名错误");
            }
            else if (user.getStatus() != 1){
                System.out.println("您的账号未激活或被冻结,请联系客服");
                model.addAttribute("loginerror","您的账号未激活或被冻结,请联系客服");
            }
            else{
                System.out.println("密码错误");
                model.addAttribute("loginerror","密码错误");
            }

            return "index";
        }*/

    }


    @RequestMapping("/register")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/toUserLogin")
    public String toUserLogin(){
        return "login";
    }





    @RequestMapping("/toAdminLogin")
    public String toAdminLogin(){
        return "admin/adminlogin";
    }


    /*
        提交注册执行的controller
        形参列表
            User user： 从前端接收过来的user对象
            String checkcode： 从前端接收过来的图片验证码 （它没有封装在user对象里面，所以单独创建一个变量接收它）
     */
    @RequestMapping("/register/submit")
    public String submitRegister(User user,@RequestParam("checkcode") String checkcode, Model model, HttpSession session){

        //给新注册用户一个随机的头像
        user.setAvatar("/user-avatar/useravatar"+(int) (1 + Math.random() * (12 - 1 + 1))+".jpg");
        //先打印出来user 我看看到底是个啥！
        System.out.println(user);
        List<String> errorList = new ArrayList<>();
        User userByNickname = userService.getUserByNickname(user.getNickname());
        User userByEmail = userService.getUserByEmail(user.getEmail());
        if (userByNickname != null){
            errorList.add("昵称重复了, 请换个昵称");
        }
        if (userByEmail != null){
            errorList.add("邮箱被注册, 请换个邮箱");
        }
        /*
            为什么用 .equals 而不用 ==
            简单介绍
            equals方法是java.lang.Object类的方法
                有两种用法说明:
            一、对于字符串变量来说，使用“==”和“equals()”方法比较字符串时，其比较方法不同。
             1、“==”比较两个变量本身的值，即两个对象在内存中的首地址。
                (java中，对象的首地址是它在内存中存放的起始地址，它后面的地址是用来存放它所包含的各个属性的地址，所以内存中会用多个内存块来存放对象的各个参数，而通过这个首地址就可以找到该对象，进而可以找到该对象的各个属性)
             2、“equals()”比较字符串中所包含的内容是否相同。
        */
        if (!checkcode.equalsIgnoreCase((String) session.getAttribute("verifyCodeValue"))){ //字符串比较忽略大小写 验证码不区分大小写
            //下面2行是调试信息
            System.out.println("用户输入的图片验证码是："+checkcode);
            System.out.println("正确的图片验证码是："+session.getAttribute("verifyCodeValue"));

            errorList.add("验证码错误, 请重新输入");
        }

        //如果以上都没有出现错误, 那说明注册成功了一半，去邮件激活页面。
        if (errorList.size() == 0){
            userService.insertUser(user);
            //将该user存入session
            session.setAttribute("recentuser",user);

            //去邮件激活页面
            return "activationemail";
        }else{
            //将全部出错信息打印到控制台, 方便调试
            for (String s : errorList) {
                System.out.println(s);
            }
            //向前端回显错误信息
            model.addAttribute("errorList", errorList);
            //向前端回显用户数据(用户当然不想注册错误之后又要重新填写表单) 在register里面搜这两个属性,就能找到在哪用了
            model.addAttribute("recentemail",user.getEmail());
            model.addAttribute("recentnickname",user.getNickname());

            return "register";
        }
    }
    @RequestMapping("/register/active")
    public String active(@RequestParam("emailcode") String emailcode, HttpSession session, Model model){

        if (!emailcode.equals(session.getAttribute("emailCode"))){
            //下面两行是调试信息
            System.out.println("用户输入的邮箱码是："+emailcode);
            System.out.println("正确的邮箱码是："+session.getAttribute("emailCode"));

            //如何回显到前端
            model.addAttribute("emailerror", "邮箱验证码有误, 10秒后可重新点击发送激活邮件");
            System.out.println("邮箱验证码有误, 10秒后可重新点击发送激活邮件");
            return "activationemail";
        }else {
            //修改激活状态 改成激活
            User recentuser = (User) session.getAttribute("recentuser");
            User userByEmail = userService.getUserByEmail(recentuser.getEmail());

            System.out.println(userByEmail);
            userService.updateStatus(userByEmail.getUid(), 1);

            //清理掉session信息invalid 此方法有风险, 难道会清理掉所有的session ?
            session.removeAttribute("recentuser");

            //返回注册成功页面
            return "sendmailsuccess";
        }
    }


    //验证码 看不清换一张的那种
    @RequestMapping("/getVerifyCode")
    public void generate(HttpServletResponse response, HttpSession session) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String verifyCodeValue = VerifyCodeUtils.drawImg(output);

        // 将校验码保存到session中
        session.setAttribute("verifyCodeValue", verifyCodeValue);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            /*logger.info("<--验证码前端写出.出现异常-->");*/
            e.printStackTrace();

        }
    }

    //服务器给客户端发送一封注册用的激活邮件, 邮件内容是4位随机数字 这里按说应该try catch一下
    @RequestMapping("/sendmail")
    public String sendMail(@RequestParam("email")String email, HttpSession session){    //形参email, 负责接收前端的括号传参
        //产生四位随机数
        String fourRandom = MailUtils.getFourRandom();
        //将这个激活邮件的四位数字存储进session中
        session.setAttribute("emailCode", fourRandom);
        //激活邮件发送，邮件正文
        String content="欢迎您注册DajiBlog, 您的邮箱校验码为:" + fourRandom + ", 请在60秒内完成注册。";
        System.out.println(content);
        //发送邮件并设置邮件的标题 第一个参数暂且定为1134107721@qq.com 之后要换成用户输入的邮箱地址
        MailUtils.sendMail(email,content,"DajiBlog激活邮件");
        System.out.println("成功向"+ email +"发送了激活邮件");
        return "activationemail";
    }

        @RequestMapping("/logout")
        public String logout(HttpServletRequest request, HttpSession session) {

            /*
                需求: 移除model.addAttribute()向html页面添加的键值对
                解决方案: (以下两种方式都可以行得通)
                    request.removeAttribute("logineduser");
                    session.removeAttribute("logineduser");
                经实测,这两种方法都是可以移除model.addAttribute()方法添加的键值对的
                Spring实战 P152中,说model.addAttribute(),, 模型数据会作为请求属性放在请求(request)之中,
                根据书上的说法, 应该用request.removeAttribute的方式,
                但是我经过实测发现,session.removeAttribute也能成功移除.

                这里是一个待解决的知识点,解决它需要搞明白servlet
                看一下  Request域，Session域 还有httpsession request response 等等这些!

                重大更新!! 经过两行sout的控制台调试信息打印,我发现一个非常严重的问题
                不管是用request来获取logineduser还是使用session获取, 最终打印的结果都是null
                那就是logineduser根本就没有存放在request域中或是session域中
                话说回来, model.addAttribute() 是请求属性,
                好好看一下请求属性吧 到这里我的思路断了
                反正 根本不用request.removeAttribute和session.removeAttribute,
                只需要一个重定向, 就能remove掉model.addAttribute()的属性

                那么问题就精准定位在
                model方式, session方式, request方式 究竟是分别如何向前端交互数据的?
                而且它们三者在controller内部交互数据的能力又是如何?
                最后就是model方式的本质是什么, Spring实战说它就是request, 这点我要亲自测试出来
                希望这个问题能得到一个标准的答案.

            */
            session.removeAttribute("logineduser");
            //退出登录的同时，清除shiro的权限缓存
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return "redirect:/index";
        }

        @GetMapping("/authtype")
        public String toTypePage(){
            return "error/type";
        }


}
