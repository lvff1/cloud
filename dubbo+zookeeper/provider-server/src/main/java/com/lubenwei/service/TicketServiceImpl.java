package com.lubenwei.service;


import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

//zookeeper: 服务注册与发现 看第一个Service注解

@Service    //此service不是spring的service  是dubbo的service 他们长得一样 作用就是项目启动后注册到注册中心(zookeeper)
@Component //为什么不用service注解?  使用了dubbo之后尽量不要使用Service注解 因为有2个一摸一样的service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "得到了Ticket";
    }
}
