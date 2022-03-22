package com.daji.service;

import com.daji.pojo.Admin;
import com.daji.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {
    Admin findByAdminnameAndPassword(@Param("adminname") String adminname, @Param("password") String password);

    Admin findAdminByAdminname(@Param("adminname") String adminname);
}
