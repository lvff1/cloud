package cn.itcast.hotel.service;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IHotelService extends IService<Hotel> {

    public PageResult search(RequestParams params) throws IOException;

    public Map<String, List<String>> filters(RequestParams params) throws IOException;

    List<String> getSuggestions(String prefix) throws IOException;

    void deleteESById(Long msg);

    void insertOrUpdateESById(Long msg);
}
