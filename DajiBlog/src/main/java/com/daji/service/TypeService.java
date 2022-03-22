package com.daji.service;
import com.daji.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TypeService {

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

    //将传进来的【字符串】形式的id 转换成对应的List<Type> types
    List<Type> listType(String ids);
    //上面操作的正相反，将传进来的List<Type> types 转换成字符串形式的id
    String unListType(List<Type> types);

    //字符串操作， 将传进来的字符串"1,2,3" 拆分成数组： list[0]=1,list[1]=2,list[2]=3
    List<Integer> convertToList(String ids);
    //上面操作的正相反，相当于decode 将传进来的数组拆分成字符串
    String converToString(List<Integer> list);
}
