package com.daji.config;/*
package com.lubenwei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if(!registry.hasMappingForPattern("/static/**")){
            registry.addResourceHandler("/static/image/**").addResourceLocations("file:C:\\codes\\OpenSource\\DajiBlog\\web\\static\\images\\user-avatar\\"); */
/*绝对路径*//*

        }
        super.addResourceHandlers(registry);

    }
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        //logger.info("HandlerMapping");
        return super.resourceHandlerMapping();
    }

}
*/
