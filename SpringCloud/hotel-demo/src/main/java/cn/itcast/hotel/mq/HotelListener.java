package cn.itcast.hotel.mq;

import cn.itcast.hotel.constants.MqConstants;
import cn.itcast.hotel.service.IHotelService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费者监听器
 * 业务：一旦监听到了自己的队列里有[增改]或[删除]的消息，立马调用更新ES的接口，给ES做同步
 */
@Component
public class HotelListener {

    //这个Service内有更新ES的接口
    @Autowired
    private IHotelService hotelService;

    //监听酒店新增或修改业务
    @RabbitListener(queues = MqConstants.HOTEL_INSERT_QUEUE)
    public void listenerHotelInsertOrUpdate(Long msg){  //接收的msg就是id
        hotelService.insertOrUpdateESById(msg); //立马更新ES索引库
    }

    //监听酒店删除业务
    @RabbitListener(queues = MqConstants.HOTEL_DELETE_QUEUE)
    public void listenerHotelDelete(Long msg){  //接收的msg就是id
        hotelService.deleteESById(msg); //立马删除ES索引库对应文档
    }
}
