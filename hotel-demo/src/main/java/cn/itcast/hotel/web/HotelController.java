package cn.itcast.hotel.web;


import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//@RestController 相当于 @Controller+@ResponseBody
//@ResponseBody 作用在Controller的方法上：  让该方法的返回不走视图解析器，
// 也就是说返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中。
//而@RequestBody正好相反：是作用在形参列表上，用于将前台发送过来固定格式的数据【xml格式 或者 json等】封装为对应的 JavaBean， 对象，
//封装时使用到的一个对象是系统默认配置的 HttpMessageConverter进行解析，然后封装到形参上。
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private IHotelService hotelService;

    @PostMapping("/list")
    public PageResult search (@RequestBody RequestParams params) throws IOException {
        return hotelService.search(params);
    }

    @PostMapping("/filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params) throws IOException{
        return hotelService.filters(params);
    }
}
