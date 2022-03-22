package com.daji.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * 发邮件工具类
 */
public final class MailUtils {
    // 发件人称号，同邮箱地址。这里是自己的邮箱
    private static final String USER = "WZR1134107721@163.com";
    //如果是qq邮箱可以使户端授权码，或者登录密码
    private static final String PASSWORD = "RFQRKKYMLDUTLPNV";

    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            //这里如果是qq邮箱,就写qq.com 如果是163邮箱 就写.163.com
            props.put("mail.smtp.host", "smtp.163.com");

            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);
            /*
                修改默认端口号，本来是25，但是部署到Linux上出现了一堆问题
                改成465
            */
            props.put("mail.port", 465);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //产生四位随机数
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
            for(int i=1; i<=4-randLength; i++)
                fourRandom = "0" + fourRandom  ;
        }
        return fourRandom;
    }

    public static void main(String[] args) throws Exception { // 做测试用



        //测试生成四位随机数是否生效
        String fourRandom = MailUtils.getFourRandom();
        System.out.println(fourRandom);
        String content="欢迎您注册DajiBlog, 您的邮箱校验码为:" + fourRandom + ", 请在60秒内完成注册。";
        System.out.println(content);
        //发送邮件测试
        MailUtils.sendMail("1134107721@qq.com",content,"测试邮件");
        System.out.println("发送成功");
    }
}
