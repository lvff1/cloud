package cn.itcast.hotel.constants;

/*
    声明Mq的一些常量
 */
public class MqConstants {
    /**
     * 交换机
     */
    public final static String HOTEL_EXCHANGE = "hotel.topic";
    /**
     * 监听新增和修改的队列
     */
    public final static String HOTEL_INSERT_QUEUE = "hotel.insert.queue";
    /**
     * 监听删除的队列
     */
    public final static String HOTEL_DELETE_QUEUE = "hotel.delete.queue";
    /**
     * 新增或修改的Key
     */
    public final static String HOTEL_INSERT_KEY = "hotel.insert.key";
    /**
     * 删除的Key
     */
    public final static String HOTEL_DELETE_KEY = "hotel.deletekey";
}
