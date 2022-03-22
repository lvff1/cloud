package com.daji.dao;

import com.daji.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 这个Dao，只负责查询出来全部daji_ssm的数据表
 * 为了后面方便调用它，将全部daji_ssm的数据表中的数据同步到redis中
 * 该Dao没有对应的Service层。如果要用就直接注入(Autowired)这个Dao就行
 */
@Mapper
@Repository
public interface RedisSyncDao {


    //同步user表
    List<User> syncUser();

    //同步t_blog表
    List<Blog> syncT_Blog();

    //同步t_type表
    List<Type> syncT_Type();

    //同步t_comment表
    List<Comment> syncT_Comment();

    //同步t_message表
    List<Message> syncT_Message();

}
