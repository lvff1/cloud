package com.daji.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传和回显的工具类
 */
public class FileUploadUtils {

    //将文件保存到某个文件夹，返回该文件的路径
    public static String convertFileToURL(MultipartFile file) {
        //得到原本的（前端传过来的）文件名
        String OriginalFilename = file.getOriginalFilename();
        //加上时间戳 新名字 = 时间戳.原文件后缀名
        String fileName = System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        //文件存放路径。  路径的最后别忘了加两个\\ 如果不加，字符串拼接会拼到父目录里面去
        String filePath = "C:\\codes\\OpenSource\\DajiBlog_SpringBoot\\src\\main\\resources\\resources\\user-avatar";
        File dest = new File(filePath+fileName);
        //如果不存在该目录就创建文件夹
        if(!dest.getParentFile().exists())
            dest.getParentFile().mkdirs();
        try {
            //保存上传的文件
            file.transferTo(dest);
            System.out.println("图片的路径存放在："+dest.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
        //没有异常就上传成功 return的东西是 相对于WEEB-INF/pages下的 html页面能显示出来的首图
        return "../../static/images/user-avatar/"+fileName;
    }
}
