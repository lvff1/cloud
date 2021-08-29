package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    //这里的注入，必须在配置类创建了才能注入。具体在哪个配置类创建，见main方法那个类（SpringBoot主启动类）
    @Autowired
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
    }
}
