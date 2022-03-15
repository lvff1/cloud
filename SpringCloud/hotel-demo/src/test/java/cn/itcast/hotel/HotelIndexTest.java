package cn.itcast.hotel;

import org.apache.http.HttpHost;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static cn.itcast.hotel.constants.HotelConstants.MAPPING_TEMPLATE;
/*
* 该类测试对索引库进行操作
* */
public class HotelIndexTest {

    private RestHighLevelClient client;
    /*@BeforeEach注解
    在每个测试方法之前执行。
    注解在非静态方法上。
    可以重新初始化测试方法所需要使用的类的某些属性。*/
    @BeforeEach
    void setUp(){
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")    //写你ES的ip+port
                //HttpHost.create("http://127.0.0.1:9200"), 这里是个可变参数，可以传多个，
                //HttpHost.create("http://127.0.0.1:9200")  如果你将来要部署ES集群，就可以写多个
        ));
    }
    //和BeforeEach相反的一个方法，资源销毁(在测试类的最后执行这个方法)
    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    //该测试函数执行顺序：setUp() -> testInit() -> tearDown()
    @Test
    void testInit(){
        System.out.println(client);
    }

    //创建索引
    @Test
    void createHotelIndex() throws IOException {
        //1、创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        //2、准备请求的参数：DSL语句 第二个参数枚举是：application/json; charset=UTF-8
        //第一个参数为静态导包：import static MAPPING_TEMPLATE;
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        //3、发送请求  indices包含了索引库操作的所有方法, 比如create创建，delete删除等等
        IndicesClient indices = client.indices();
        //如果报错，看这篇博客，可能解决问题：https://blog.csdn.net/weixin_44757863/article/details/120853646
        //如果报错，看这篇博客，可能解决问题：https://blog.csdn.net/weixin_44757863/article/details/120853646
        //如果报错，看这篇博客，可能解决问题：https://blog.csdn.net/weixin_44757863/article/details/120853646
        indices.create (request, RequestOptions.DEFAULT);    //创建索引库
    }

    //删除索引(判断索引库是否存在，如果存在就删除)
    @Test
    void deleteHotelIndex() throws IOException {
        //先查询有没有该索引库
        GetIndexRequest request1 = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(request1, RequestOptions.DEFAULT);

        //如果没有，就删除
        DeleteIndexRequest request2 = new DeleteIndexRequest("hotel");
        if (exists){
            System.out.println("索引库有该数据。执行删除操作");
            client.indices().delete(request2, RequestOptions.DEFAULT);
        }else{
            System.out.println("索引库里没有该数据，不删除");
        }
    }
}
