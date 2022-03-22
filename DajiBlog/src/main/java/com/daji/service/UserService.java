package com.daji.service;

import com.daji.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void insertUser(User user); //添加用户

    int deleteUser(@Param("uid") int uid); //根据用户的uid来删除用户, 返回int类型删除成功与否

    User getUserById(@Param("uid") int uid);  //根据uid来查询用户

    User getUserByEmail(@Param("email") String email);  //根据邮箱查询用户

    User getUserByNickname(@Param("nickname") String nickname); //根据昵称查询用户

    String getUserRoleByEmail(@Param("email") String email);    //根据邮箱查询用户的角色（是user还是admin）

    int updateStatus(@Param("uid") int uid, @Param("status") int status);   //改变用户状态（激活与否）

    List<User> queryAllUsers(); //查询全部用户

}
