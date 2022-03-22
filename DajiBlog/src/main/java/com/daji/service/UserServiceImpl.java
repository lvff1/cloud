package com.daji.service;


import com.daji.dao.UserDao;
import com.daji.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public int deleteUser(int uid) {
        userDao.deleteUser(uid);
        return 1;
    }


    @Override
    public User getUserById(int uid) {
        return userDao.getUserById(uid);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userDao.getUserByNickname(nickname);
    }

    @Override
    public String getUserRoleByEmail(String email) {
        return userDao.getUserRoleByEmail(email);
    }

    @Override
    public int updateStatus(int uid, int status) {
        userDao.updateStatus(uid, status);
        return 1;
    }

    @Override
    public List<User> queryAllUsers() {
        return userDao.queryAllUsers();
    }

}
