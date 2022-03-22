/*
package com.daji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //这个类是我们自己的MVC配置类, 如果你这个类看不懂, 那就去看 springboot-03-web这个demo, 里面有同样的一个文件, 也叫MyMvcConfig

    */
/***
     * 静态资源放行
     * @param registry
     *//*

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/items/**").addResourceLocations("classpath:/templates/items/");
    }


}
*/
