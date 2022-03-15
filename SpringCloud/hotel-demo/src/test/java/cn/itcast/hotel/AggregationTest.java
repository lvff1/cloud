package cn.itcast.hotel;

import cn.itcast.hotel.pojo.HotelDoc;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 * RestClient 聚合
 * */

@SpringBootTest
public class AggregationTest {

    private RestHighLevelClient client;

    @Test
    void testAggregation() throws IOException {
        //1、准备request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        request.source().size(0);   //只需要聚合结果不需要文档，故把size设置成0
        request.source().aggregation(AggregationBuilders
                .terms("brandAgg")  //自定义聚合名称
                .field("brand")     //对brand字段做聚合
                .size(10)           //只聚合20条
        );
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        //4、逐层解析JSON结果
        Aggregations aggregations = response.getAggregations();
        Terms brandTerms = aggregations.get("brandAgg");
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            System.out.println(key);
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
