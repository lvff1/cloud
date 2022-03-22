package com.daji.config;


import com.daji.pojo.User;
import com.daji.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

//自定义的UserRealm 必须继承AuthorizingRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入了User的授权");
        //获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证: "+primaryPrincipal);
        //根据主身份信息获取角色信息  注意！这里只获取了角色信息，没有具体到每个角色的权限。还没到设计权限的复杂程度
        String userRoleByEmail = userService.getUserRoleByEmail(primaryPrincipal);
        //创建info
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色，数据库里取到什么角色，就添加什么角色
        simpleAuthorizationInfo.addRole(userRoleByEmail);
        //假如是一个admin，那么就额外添加上user的角色
        if (userRoleByEmail.equals("admin")){
            System.out.println("我是admin角色，所以理所当然具有user角色的全部功能");
            simpleAuthorizationInfo.addRole("user");
        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入了User的认证");
        //根据身份信息 我sout了一下principal 发现它就是账号名
        String principal = (String) token.getPrincipal();
        //注：这里的userservice如何拿到，不一定通过用工厂的方式，参考狂神是如何拿到的这个userservice，他是直接autowired进去的
        User user = userService.getUserByEmail(principal);
        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getEmail(),user.getPwd(),this.getName());
        }
        return null;
    }
}
