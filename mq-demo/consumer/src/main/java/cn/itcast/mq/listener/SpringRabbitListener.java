package cn.itcast.mq.listener;

import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@Component
public class SpringRabbitListener {

    //注释掉简单队列的代码，用的时候可以展开，但是要注释掉其他的代码
    /*@RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg){
        System.out.println("消费者已经接受到消息："+msg);
    }*/

    //----------------WorkQueue开始-----------------
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1已经接受到消息："+msg);
        Thread.sleep(20);
    }
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2........已经接受到消息："+msg);
        Thread.sleep(200);  //这里的速度比消费者1慢
    }
    //----------------WorkQueue结束-----------------

    //----------------------FanoutQueue开始----------------------
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg){
        System.out.println("消费者1接受到fanoutqueue1消息："+msg);
    }
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg){
        System.out.println("消费者2接受到fanoutqueue2消息："+msg);
    }
    //----------------------FanoutQueue结束----------------------



}
