package com.daji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
/*
    下一行我注释掉了，因为项目上线要关闭swagger
 */
//@EnableSwagger2 //开启swagger
public class SwaggerConfig {

    //配置Swagger 的 Docket的Bean实例
    @Bean
    public Docket docket(Environment environment){

        //设置要显示的swagger环境  这里选择dev环境(application-dev.properties)
        //Profiles profiles = Profiles.of("dev","text");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        //boolean b = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("大吉")
                .select()
                //RequestHandlerSelectors: 配置要扫描接口的方式
                // basePackage:配置包扫描(最常用!)
                // 如果你配置any():扫描全部 如果你配置none():不扫描
                // 如果你配置withClassAnnotation():扫描类上的注解, 参数是一个注解的反射对象
                // 如果你配置withMethodAnnotation():扫描方法上的注解 例: RequestHandlerSelectors.withMethodAnnotation(PostMapping.class) 意思是扫描注解了@PostMapping的方法
                .apis(RequestHandlerSelectors.basePackage("com.daji.controller"))
                //过滤xx路径 过滤掉(不扫描)路径里的东西!! 下一行注释掉了 假如这么配置,lubenwei包下的任何东西都会被过滤,程序将无意义
                //.paths(PathSelectors.ant("/lubenwei/**"))
                .build();   //build 工厂模式
    }
    //配置swagger 信息
    private ApiInfo apiInfo(){
        Contact DEFAULT_CONTACT = new Contact("大吉", "", "1134107721@qq.com");
        return new ApiInfo("大吉的SwaggerAPI文档",
                "测试Controller的接口",
                "1.0",
                "",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
