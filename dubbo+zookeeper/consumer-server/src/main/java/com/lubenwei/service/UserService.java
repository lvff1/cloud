package com.lubenwei.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service    //放到容器中 这里的@Service是spring的
public class UserService {
    //想拿到provider-server提供的票, 要去注册中心拿到服务

    /*
        @Reference 引用, 有2种方法 :
            1.pom坐标
            2.可以定义路径相同的接口名 (下面代码用这个)
            注意!注意!注意! 两个模块必须相同路径 TicketService这个接口, 必须都处在com.lubenwei.service包下!
     */
    @Reference
    TicketService ticketService;

    public void buyTicket(){
        //这个TicketService ticketService是接口,根本就没实现,但是我们却能使用它在另一个模块的实现类
        String ticket = ticketService.getTicket();
        System.out.println("在注册中心拿到:"+ticket);
    }
}
