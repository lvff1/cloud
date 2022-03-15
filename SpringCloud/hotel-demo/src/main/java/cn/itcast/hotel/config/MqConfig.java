package cn.itcast.hotel.config;

import cn.itcast.hotel.constants.MqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ配置类（当然也可以写成注解形式，更方便。这里使用手写配置类方式）
 * 可以参考之前MQ工程的代码樣例
 */
@Configuration
public class MqConfig {

    //声明交换机
    @Bean
    public TopicExchange topicExchange(){
        //还有两个可选参数分别是true和false。我点进去看源码发现不用填默认就是true和false，所以这里不管
        return new TopicExchange(MqConstants.HOTEL_EXCHANGE);
    }

    //声明队列：Add/Insert
    @Bean
    public Queue insertQueue(){
        return new Queue(MqConstants.HOTEL_INSERT_QUEUE);
    }
    //声明队列：delete
    @Bean
    public Queue deleteQueue(){
        return new Queue(MqConstants.HOTEL_DELETE_QUEUE);
    }

    //定义绑定关系,绑定两个队列到交换机
    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.HOTEL_INSERT_KEY);
    }
    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.HOTEL_DELETE_KEY);
    }
}
