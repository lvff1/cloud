package cn.itcast.hotel.service.impl;

import cn.itcast.hotel.mapper.HotelMapper;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RestHighLevelClient client;


    @Override
    public PageResult search(RequestParams params) throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        String key = params.getKey();
        //2.1 构建BooleanQuery(多条件复合查询)和FunctionScoreQuery算分函数
        buildBasicQuery(params, request);

        //2.2 分页：
        Integer page = params.getPage();
        Integer size = params.getSize();
        request.source().from((page-1)*size).size(size);
        //2.3 按前端传过来的地理位置排序
        String location = params.getLocation();
        if (location!=null && !location.equals("")){
            request.source().sort(SortBuilders
                    .geoDistanceSort("location",new GeoPoint(location))
                    .order(SortOrder.ASC)
                    .unit(DistanceUnit.KILOMETERS)
            );
            System.out.println("已经经过地理位置由近到远排序。");
        }
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4、解析响应
        return handleResponse(response);
    }

    @Override
    public Map<String, List<String>> filters(RequestParams params) throws IOException {
        //1、准备request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        //2.5 限定聚合范围 —— 只有针对查出来的数据才进行聚合
        buildBasicQuery(params, request);//这行代码和之前查询的条件完全一样
        request.source().size(0);   //只需要聚合结果不需要文档，故把size设置成0
        buildAggregation(request);  //拼装聚合条件
        //3、发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        //4、逐层解析JSON结果
        Aggregations aggregations = response.getAggregations();
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        //解析聚合结果，将多种聚合结果添加到不同的list中，并将不同的list添加到Map的不同key中，返回给前端
        List<String> brandList = getAggByName(aggregations, "brandAgg");
        result.put("brand",brandList);
        List<String> cityAgg = getAggByName(aggregations, "cityAgg");
        result.put("city",cityAgg);
        List<String> starAgg = getAggByName(aggregations, "starAgg");
        result.put("starName",starAgg);
        return result;
    }

    @Override
    public List<String> getSuggestions(String prefix) throws IOException {
        //1、准备request
        SearchRequest request = new SearchRequest("hotel");
        //2、准备DSL
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions",  //自定义的补全查询名称
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix(prefix)
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
        List<String> list = new ArrayList<>(options.size());
        for (CompletionSuggestion.Entry.Option option : options) {
            String text = option.getText().toString();
            list.add(text);
        }
        return list;
    }

    @Override
    public void deleteESById(Long msg) {
        DeleteRequest request = new DeleteRequest("hotel", msg.toString());
        try {
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertOrUpdateESById(Long msg) {
        //先根据id去数据库查出来酒店数据，然后把该数据发送到索引库里面去
        Hotel hotel = getById(msg);
        //将数据库数据转换为索引库数据
        HotelDoc hotelDoc = new HotelDoc(hotel);
        //1、准备Request对象
        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());
        //2、准备Json文档(包含了FastJSON的序列化操作)
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        //3、发送请求(发送的方法和操作索引库的方法不同了)
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> getAggByName(Aggregations aggregations, String aggName) {
        Terms brandTerms = aggregations.get(aggName);
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        List<String> resultList = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            resultList.add(key);
        }
        return resultList;
    }

    /*
        封装 拼装聚合条件的类
     */
    private void buildAggregation(SearchRequest request) {
        request.source().aggregation(AggregationBuilders
                .terms("brandAgg")  //自定义聚合名称
                .field("brand")     //对brand字段做聚合
                .size(100)           //只聚合20条
        );
        request.source().aggregation(AggregationBuilders
                .terms("cityAgg")  //自定义聚合名称
                .field("city")     //对brand字段做聚合
                .size(100)           //只聚合20条
        );
        request.source().aggregation(AggregationBuilders
                .terms("starAgg")  //自定义聚合名称
                .field("starName")     //对brand字段做聚合
                .size(100)           //只聚合20条
        );
    }

    private void buildBasicQuery(RequestParams params, SearchRequest request) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //关键字搜索
        String key =  params.getKey();
        if (key ==null || key.equals("")){   //如果页面传过来查询条件为空，直接查出所有
            boolQuery.must(QueryBuilders.matchAllQuery());
        }else {     //根据页面传过来的查询条件进行查询
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        }
        //-----------------------条件过滤----------------
        //城市条件
        if (params.getCity() != null && !params.getCity().equals("")){
            //这里之所以使用filter而不是使用must，因为这是过滤条件，没必要参与算分，参与算分的话会影响性能
            //这里之所以使用termQuery(精确查询不分词) 而不是 matchQuery(用分词器分一下词)，是因为该字段是个精确值，不能再分了
            boolQuery.filter(QueryBuilders.termQuery("city", params.getCity()));
        }
        //品牌条件
        if (params.getBrand() != null && !params.getBrand().equals("")){
            //这里之所以使用filter而不是使用must，因为这是过滤条件，没必要参与算分，参与算分的话会影响性能
            //这里之所以使用termQuery(精确查询不分词) 而不是 matchQuery(用分词器分一下词)，是因为该字段是个精确值，不能再分了
            boolQuery.filter(QueryBuilders.termQuery("brand", params.getBrand()));
        }
        //星级条件
        if (params.getStartName() != null && !params.getStartName().equals("")){
            //这里之所以使用filter而不是使用must，因为这是过滤条件，没必要参与算分，参与算分的话会影响性能
            //这里之所以使用termQuery(精确查询不分词) 而不是 matchQuery(用分词器分一下词)，是因为该字段是个精确值，不能再分了
            boolQuery.filter(QueryBuilders.termQuery("starName", params.getStartName()));
        }
        //价格条件（范围过滤）
        if (params.getMinPrice() != null && params.getMaxPrice() != null){
            boolQuery.filter(QueryBuilders.
                    rangeQuery("price").gte(params.getMinPrice()).lte(params.getMaxPrice()));
        }

        //控制算分（给了广告费算分就高，排名就靠前）
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(
                //把原始的查询传进来（原始的查询包含了原始的分值）
                boolQuery,
                //function score的数组
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        //数组中的一个具体的function score元素
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                //过滤条件,索引库里如果字段isAD是true，就挑出来
                                QueryBuilders.termQuery("isAD",true),
                                //算分函数  权重直接×10倍
                                ScoreFunctionBuilders.weightFactorFunction(10)
                        )
                });
        //将该复合查询添加进request里面
        request.source().query(functionScoreQuery);
    }

    //抽取出来的解析响应的公共方法（因为上面所有方法解析响应的代码都是一样的）
    private PageResult handleResponse(SearchResponse response){
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value; //获取总条数
        SearchHit[] hits = searchHits.getHits();
        List<HotelDoc> hotels = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();//可以点进去看看源码，是将source字段转换成json了
            //可选：将该json序列化为对象
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            //获取排序值（你现在这台设备所在位置和ES所在点地理位置的差值，也就是距离）
            Object[] sortValues = hit.getSortValues();
            if (sortValues.length > 0){
                Object sortValue = sortValues[0];
                hotelDoc.setDistance(sortValue);
            }
            hotels.add(hotelDoc);
        }
        System.out.println(hotels);
        return new PageResult(total,hotels);
    }
}
