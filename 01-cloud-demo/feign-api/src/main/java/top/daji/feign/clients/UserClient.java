package top.daji.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.daji.feign.config.DefaultFeignConfiguration;
import top.daji.feign.pojo.User;

//Feign配置类局部有效（声明configuration = DefaultFeignConfiguration.class）：
//@FeignClient(name = "userservice",configuration = DefaultFeignConfiguration.class)
@FeignClient(name = "userservice")
@Component
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}
