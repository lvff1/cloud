package cn.itcast.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/*
 * RestClient 自动补全
 * */

@SpringBootTest
public class SuggestTest {

    private RestHighLevelClient client;

    @Test
    void testSuggest() throws IOException {
        //1、准备request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions",  //自定义的补全查询名称
                SuggestBuilders.completionSuggestion("suggestion")
                .prefix("h")
                .skipDuplicates(true)
                .size(10)
        ));
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        //4、逐层解析结果
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestions = suggest.getSuggestion("suggestions"); //填写上面你自定义好的补全查询名称
        //获取option
        List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
        for (CompletionSuggestion.Entry.Option option : options) {
            String text = option.getText().toString();
            System.out.println(text);
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
