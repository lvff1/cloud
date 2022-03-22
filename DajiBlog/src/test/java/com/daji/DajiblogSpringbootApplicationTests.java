package com.daji;

import com.daji.dao.RedisSyncDao;
import com.daji.pojo.*;
import com.daji.service.BlogService;
import com.daji.service.TypeService;
import com.daji.vo.BlogQuery;
import com.daji.vo.ShowBlog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class DajiblogSpringbootApplicationTests {

    @Autowired
    @Qualifier("redisTemplate") //这里的意思是指定我们自己配置的RedisConfig.java 里面的那个redisTemplate。而不是用系统默认的
    private RedisTemplate redisTemplate;
    @Autowired
    public BlogService blogService;
    @Autowired
    public RedisSyncDao redisSyncDao;
    @Autowired
    public TypeService typeService;

    @Test
    void contextLoads() {
        System.out.println("/user-avatar/useravatar"+(int) (1 + Math.random() * (12 - 1 + 1))+".jpg");
    }

    /*@Test
    void testConnect() {
        redisTemplate.opsForValue().set("mykey","hello redis-integration");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }*/

    //运行此方法则同步数据库中所有表到redis中
    /*@Test
    void RedisSync() {

        //同步User表
        List<User> users = redisSyncDao.syncUser();
        for (User user : users) {
            String userKey = "user:" + user.getUid();
            //在redis中存键值对
            redisTemplate.opsForValue().set(userKey, user);
        }

        //同步t_blog表
        List<Blog> blogs = redisSyncDao.syncT_Blog();
        for (Blog blog : blogs) {
            String blogKey = "blog:" + blog.getId();
            redisTemplate.opsForValue().set(blogKey, blog);
        }

        //同步t_type表
        List<Type> types = redisSyncDao.syncT_Type();
        for (Type type : types) {
            String typeKey = "type:" + type.getId();
            redisTemplate.opsForValue().set(typeKey, type);
        }

        //同步t_comment表
        List<Comment> comments = redisSyncDao.syncT_Comment();
        for (Comment comment : comments) {
            String commentKey = "comment:" + comment.getId();
            redisTemplate.opsForValue().set(commentKey, comment);
        }

        //同步t_message表
        List<Message> messages = redisSyncDao.syncT_Message();
        for (Message message : messages) {
            String messageKey = "message:" + message.getId();
            redisTemplate.opsForValue().set(messageKey, message);
        }

    }*/

    /*@Test
    void test1() {
        //在redis中取键值对
        Blog blog = (Blog) redisTemplate.opsForValue().get("blog:1");
        System.out.println(blog);
    }*/

    @Test
    void test2() {
        List<Type> typeByBlogId = typeService.getTypeByBlogId(20);
        for (Type type : typeByBlogId) {
            System.out.println(type);
        }
    }

    public String testReturn(){
        int i = 1;
        try{
            return "a"+(i++);
        }catch (Exception e){
            return "b"+(i++);
        }finally {
            return "c"+(i++);
        }
    }
    @Test
    void test3() {
        System.out.println(testReturn());

    }


}
