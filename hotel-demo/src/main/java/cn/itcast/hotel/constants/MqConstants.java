package cn.itcast.hotel.constants;

/*
    声明Mq的一些常量
    新增和修改对ES来讲，业务是同一个（接口都是一个插入接口，有则修改无则新增）
    删除对于ES来讲就不同了
    所以要分开不同的key，弄成不同queue，到时候ES接收处理也分两种情况处理。
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
