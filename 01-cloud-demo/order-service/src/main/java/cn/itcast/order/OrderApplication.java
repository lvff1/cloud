package cn.itcast.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import top.daji.feign.clients.UserClient;
import top.daji.feign.config.DefaultFeignConfiguration;

@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
//clients: 跨工程注入bean失败问题解决
//defaultConfiguration: Feign配置类全局有效(如果想局部有效就声明在UserClient这个类上)：
@EnableFeignClients(clients = UserClient.class, defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    //按理说应该在配置文件里注入的，为了简单起见，主启动类也是配置文件，就在这里注入了。
    @Bean
    @LoadBalanced   //负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}