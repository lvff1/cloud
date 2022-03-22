package com.daji.dao;


import com.daji.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {

    //增
    int insertType(Type type);

    //删
    int deleteType(@Param("id") int id);

    //改
    int updateType(@Param("id") int id, @Param("name") String name);

    //查
    Type getTypeById(@Param("id") int id);

    Type getTypeByName(@Param("name") String name);

    List<Type> getTypeByBlogId(@Param("id")int id);

    //查询全部Type
    List<Type> queryAllTypes();
}
