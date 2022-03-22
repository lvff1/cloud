package com.daji.config;



import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //第三步: ShiroFilterFactoryBean (过滤对象)    这里的Qualifier标记的形参,和第二步创建出来的方法DefaultWebSecurityManager绑定
    //其实质上是一个filter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){

        System.out.println("进入了ShiroConfig中");

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anno:无需认证就可以访问
            authc: 必须认证了才能访问
            user: 必须拥有 记住我 功能才能用
            perms: 拥有对某个资源的权限才能访问
            role: 拥有某个角色权限才能访问
        */

        //配置系统公共资源
        Map<String,String> map = new HashMap<String,String>();

        //1. 所有的静态资源全都是公共的
        map.put("classpath:static/**","anon");
        map.put("classpath:resources/**","anon");
        /*
            2.
            下面的公共资源来自于UserController
            这里是程序开发初期接口设计的严重失误，才导致了今日的状况
            本来只需要配置： map.put("/user/**","anon"); 一行就能解决问题。但是我却把user下的资源分开写了
         */
        map.put("/login","anon");
        map.put("/register/**","anon"); //anon 设置为公共资源  放行资源放在下面
        map.put("/getVerifyCode","anon");
        map.put("/sendmail","anon");
        //index有好几个资源(详见indexController)
        map.put("/index","anon");
        map.put("/","anon");
        map.put("/index.html","anon");
        //logout好像shiro有专门的处理。这里先配上，别忘了取消
        map.put("/logout","anon");
        //3. 管理员登陆页 和 管理员登录请求
        map.put("/toAdminLogin","anon");
        map.put("/adminlogin","anon");


        //配置系统受限资源 其他所有都是受限资源
        //map.put("/**","authc");//authc 请求这个资源需要认证和授权

        //user身份区别于游客身份(无身份)，user身份的特权是：评论
        map.put("/authtype","roles[user]");
        //admin下面所有请求，必须有admin身份才能访问
        map.put("/admin/**","roles[admin]");

        /*filterMap.put("/user/add","authc"); //必须认证了才能访问add页面
        filterMap.put("/user/update","authc");  //必须认证了才能访问update页面*/
        //filterMap.put("/user/*","authc");  //上面2行可以精简成1行, 用通配符*

        bean.setFilterChainDefinitionMap(map);
        //设置登陆的请求.也就是说如果你没有权限, 会跳转到管理员登陆页面, 那登陆页面在哪里找呢, 就在下面这行代码
        bean.setLoginUrl("/toAdminLogin");
        //设置未授权页面  对应主键(就在这个文件里面 ctrl+f): 未授权    这里的/unauth是MyController下的
        bean.setUnauthorizedUrl("/unauth");

         return bean;
    }


    //第二步: DefaultWebSecurityManager 安全     后面的@Qualifier标记的形参, 和第一步创建出来的方法UserRealm绑定
    //怎么绑定的我也不知道, 去看一下@Qualifier
    @Bean(name="securityManager")   //Bean后面加name是取别名
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        System.out.println("进入了第二步，绑定了realm");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(realm);
        return securityManager;
    }


    //第一步：创建自定义realm
    @Bean
    public Realm getRealm(){
        UserRealm userRealm = new UserRealm();
        System.out.println("获取了userRealm");
        return userRealm;
    }



}
