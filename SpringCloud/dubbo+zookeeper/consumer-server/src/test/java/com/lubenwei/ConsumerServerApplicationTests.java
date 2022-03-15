package com.lubenwei;

import com.lubenwei.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerServerApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.buyTicket();
    }

}
