package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.PrimaryMissingActionException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static cn.itcast.hotel.constants.HotelConstants.MAPPING_TEMPLATE;

/*
 * 该类测试对文档进行操作
 * 包含了四个方法：增删改查
 * */

/*
 *   总结下这组API的共同特点：
 *   1、都需要初始化RestHighLevelClient
 *   2、创建xxxRequest。xxx代表Index(增)、Get、Update、Delete
 *   3、准备参数（增和改需要）
 *   4、发送请求，调用RestHighLevelCLient的xxx方法，xxx代表Index(增)、Get、Update、Delete
 *   5、如果是Get(查)，则需要解析查询结果(JSON反序列化成对象)
 *
 * */
@SpringBootTest
public class HotelDocumentTest {
    //注入该service，准备对数据库进行操作（查出酒店数据）
    @Autowired
    private IHotelService hotelService;


    private RestHighLevelClient client;

    @Test
    void testAddDocument() throws IOException {
        //根据id查询酒店数据
        Hotel hotel = hotelService.getById(45870L);
        //将数据库数据转换为索引库数据
        HotelDoc hotelDoc = new HotelDoc(hotel);
        //1、准备Request对象
        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());
        //2、准备Json文档(包含了FastJSON的序列化操作)
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        //3、发送请求(发送的方法和操作索引库的方法不同了)
        client.index(request, RequestOptions.DEFAULT);
        //如果发送成功，去ES控制台执行ES语句：GET /hotel/_doc/45870 如果能查到我们刚刚插入的数据，说明测试成功！
        //如果发送成功，去ES控制台执行ES语句：GET /hotel/_doc/45870 如果能查到我们刚刚插入的数据，说明测试成功！
    }

    @Test
    void testGetDocumentById() throws IOException {
        //1、准备Request对象
        GetRequest request = new GetRequest("hotel", "45870");
        //2、发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //3、解析响应结果
        String json = response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("hotel", "45870");
        //这里的请求参数有点特殊 可变参数
        request.doc(
                "price", "952",
                "starName", "0星"
        );
        client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("hotel", "45870");
        client.delete(request, RequestOptions.DEFAULT);
    }

    /*
     * 需求：批量查询酒店数据，然后批量导入到索引库中
     * 思路：
     *   1、从数据库查出来一大堆酒店数据
     *   2、数据库类型转换成ES类型
     *   3、利用JavaRestClient中的Bulk批处理实现批量新增
     *   4、新增完成后，去kibana控制台执行命令：GET /hotel/_search 查询出所有结果，发现已经全部导入了
     * */
    @Test
    void testBulkRequest() throws IOException {
        //批量查询酒店数据（查出所有的酒店数据）
        List<Hotel> hotels = hotelService.list();

        //1、创建request
        BulkRequest request = new BulkRequest();
        //2、准备参数，添加多个新增的Request
        //转换成文档类型
        for (Hotel hotel : hotels) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            request.add(new IndexRequest("hotel")
                    .id(String.valueOf(hotelDoc.getId()))
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        }
        //3、发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }


    /*@BeforeEach注解
    在每个测试方法之前执行。
    注解在非静态方法上。
    可以重新初始化测试方法所需要使用的类的某些属性。*/
    @BeforeEach
    void setUp() {
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
    void testInit() {
        System.out.println(client);
    }

}
