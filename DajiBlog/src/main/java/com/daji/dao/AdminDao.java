package com.daji.dao;

import com.daji.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDao {
    Admin findByAdminnameAndPassword(@Param("adminname") String adminname, @Param("password") String password);

    Admin findAdminByAdminname(@Param("adminname") String adminname);  //根据名字查询Admin
}
