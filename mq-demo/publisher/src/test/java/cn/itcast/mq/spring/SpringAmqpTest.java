package cn.itcast.mq.spring;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;  //注入进来的这玩意并不存在于我们的工程里，是spring的jar包提供的

    //FIXME SpringAMQP如下面的发送有个问题：只能发送已经存在的队列。如果队列不存在发送就失效
    //没有创建队列的代码,创建队列的代码见原生的rabbitmq的demo
    //SpringAMQP创建队列的代码如下：
    //@Bean
    //    public Queue testQueue(){
    //        return new Queue("test.queue");
    //    }
    @Test
    public void simpleQueue(){
        String queueName = "simple.queue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName,message);
        System.out.println("发送完成");
    }

    @Test
    public void workQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message-";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName,message+i);
            Thread.sleep(20);   //方便看执行过程，别发的太快了
            System.out.println("已发送："+i+"条到队列中");
        }
    }

    //测试FanoutQueue的发送队列：
    //以前是直接发送到队列，现在是要发送到交换机，所以代码略有不同
    /*
        可以发现，FanoutQueue增加了一层交换机，可以多个队列对应多个消费者。
        而且比起WorkQueue，FanoutQueue生产者是先发送到交换机; 而WorkQueue是直接发送到队列
    */
    @Test
    public void testSendFanoutExchange(){
        //交换机名称
        String exchangeName = "itcast.fanout";
        //消息
        String message = "hello, every one!";
        //发送消息到交换机（不是之前的发送到队列了）
        //三个入参，分别是交换机名称、 routingKey(先不管，后面会讲到) 、消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }
}
