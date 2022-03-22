package com.daji.service;


import com.daji.dao.AdminDao;
import com.daji.dao.UserDao;
import com.daji.pojo.Admin;
import com.daji.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;


    @Override
    public Admin findByAdminnameAndPassword(String adminname, String password) {
        return adminDao.findByAdminnameAndPassword(adminname, password);
    }

    @Override
    public Admin findAdminByAdminname(String adminname) {
        return adminDao.findAdminByAdminname(adminname);
    }
}
