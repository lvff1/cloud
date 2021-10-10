package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.daji.feign.clients.UserClient;
import top.daji.feign.pojo.User;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;


    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2、用feign远程调用
        User user = userClient.findById(order.getUserId());

        //3、 封装user到Order
        order.setUser(user);

        // 4.返回
        return order;
    }

    //TODO 下面是resttemplate发送http请求的代码，为了测试feign，注释掉了。如果要用可以打开
    //这里的注入，必须在配置类创建了才能注入。具体在哪个配置类创建，见main方法那个类（SpringBoot主启动类）
    /*@Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2、利用restTemplate发起http请求，查询用户
        String url = "http://userservice/user/" + order.getUserId();    //由于已经在Eureka里面配置了服务，这里只需要写配置的服务名即可
        User user = restTemplate.getForObject(url, User.class);//发送http请求,第二个参数是预期返回类型，可以返回json也可以返回对象
        //3、 封装user到Order
        order.setUser(user);

        // 4.返回
        return order;
    }*/
}
