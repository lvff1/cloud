package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 * RestClient 查询文档
 * */

@SpringBootTest
public class HotelSearchTest {

    private RestHighLevelClient client;

    //查询所有
    @Test
    void testMatchAll() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        request.source().query(QueryBuilders.matchAllQuery());   //得到所有结果
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("hotelDoc = "+hotelDoc);
        }

    }

    //带条件查询
    @Test
    void testMatch() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL   得到name字段包含如家的结果
        request.source().query(QueryBuilders.matchQuery("name","如家"));
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("hotelDoc = "+hotelDoc);
        }

    }
    //精确查询：term、range查询
    //复合查询：boolean query
    //查询目标：组合查询条件，将city字段为杭州的记录且price字段小于等于250的记录给查出来
    @Test
    void testBool() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        //2.1 准备BooleanQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //2.2 添加term
        boolQuery.must(QueryBuilders.termQuery("city", "杭州"));  //must条件
        //2.3 添加range
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));  //filter条件
        request.source().query(boolQuery);
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("hotelDoc = "+hotelDoc);
        }

    }

    //分页和排序
    @Test
    void testPageAndSort() throws IOException {
        //页码，每页大小
        int page = 1, size = 5;

        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        //排序 sort
        request.source().sort("price", SortOrder.ASC);
        //分页 from size
        request.source().from((page-1) * size).size(5);
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("hotelDoc = "+hotelDoc);
        }

    }

    //高亮
    @Test
    void testHighLight() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        request.source().query(QueryBuilders.matchQuery("name", "如家"));
        //高亮
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应(高亮结果的解析和之前的所有方法都不一样！因为之前getSourceAsString只是得到结果json的_source字段)
        //但是高亮的结果是存放于 highlight字段的（如以下代码所示）：
        /*
            {
                "_index" : "hotel",
                "_type" : "_doc",
                "_id" : "2316304",
                "_score" : 3.7319894,
                "_source" : {
                  "address" : "龙岗街道龙岗墟社区龙平东路62号",
                  "name" : "如家酒店(深圳双龙地铁站店)",
                  "pic" : "https://m.tuniucdn.com/fb3/s1/2n9c/4AzEoQ44awd1D2g95a6XDtJf3dkw_w200_h200_c1_t0.jpg",
                  "starName" : "二钻"
                },
                "highlight" : {
                  "name" : [
                    "<em>如</em><em>家</em>酒店(深圳双龙地铁站店)"
                  ]
                }
              }
        */
        //因此高亮结果解析应该不同于之前_source字段的解析
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//获取_source
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);

            //获取高亮结果(如果追求鲁棒性可以做非空校验。这里没做)
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField field = highlightFields.get("name");
            //获取高亮值
            String highlightName = field.getFragments()[0].string();
            //覆盖非高亮结果：
            hotelDoc.setName(highlightName);
            System.out.println("hotelDoc = "+hotelDoc);
        }

    }

    //抽取出来的解析响应的公共方法（因为上面所有方法解析响应的代码都是一样的）
    private void handleResponse(SearchResponse response){
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("hotelDoc = "+hotelDoc);
        }
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
