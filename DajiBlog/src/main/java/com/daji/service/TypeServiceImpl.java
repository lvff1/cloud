package com.daji.service;

import com.daji.dao.TypeDao;
import com.daji.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;



    @Override
    public int insertType(Type type) {
        typeDao.insertType(type);
        return 1;
    }

    @Override
    public int deleteType(int id) {
        typeDao.deleteType(id);
        return 1;
    }

    @Override
    public int updateType(int id, String name) {
        typeDao.updateType(id, name);
        return 1;
    }

    @Override
    public Type getTypeById(int id) {
        return typeDao.getTypeById(id);
    }




    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    public List<Type> getTypeByBlogId(int id) {
        return typeDao.getTypeByBlogId(id);
    }

    @Override
    public List<Type> queryAllTypes() {
        return typeDao.queryAllTypes();
    }

    //将传进来的【字符串】形式的id 转换成对应的List<Type> types
    @Override
    public List<Type> listType(String ids) {
        //调用下面的字符串操作方法，得到包括id的数组
        List<Integer> list = convertToList(ids);
        List<Type> types = new ArrayList<Type>();
        for (Integer typeId : list) {
            Type typeById = getTypeById(typeId);
            types.add(typeById);
        }
        return types;
    }
    //上面操作的正相反，将传进来的List<Type> types 转换成字符串形式的id
    @Override
    public String unListType(List<Type> types) {
        List<Integer> list = new ArrayList<Integer>();
        for (Type type : types) {
            int id = type.getId();
            list.add(id);
        }
        return converToString(list);
    }

    //字符串操作， 将传进来的字符串"1,2,3" 拆分成数组： list[0]=1,list[1]=2,list[2]=3
    @Override
    public List<Integer> convertToList(String ids){
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }
    //上面操作的正相反，相当于decode 将传进来的数组拆分成字符串
    @Override
    public String converToString(List<Integer> list) {
        String s = "";
        if (list.size() != 0){
            for (Integer lis : list) {
                s = s + lis + ",";
            }
        }
        //截掉字符串最后一个逗号
        return s.substring(0,s.length() - 1);
    }
}
