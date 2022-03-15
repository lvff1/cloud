package cn.itcast.hotel.web;

import cn.itcast.hotel.constants.MqConstants;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/{id}")
    public Hotel queryById(@PathVariable("id") Long id){
        return hotelService.getById(id);
    }

    @GetMapping("/list")
    public PageResult hotelList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1") Integer size
    ){
        Page<Hotel> result = hotelService.page(new Page<>(page, size));

        return new PageResult(result.getTotal(), result.getRecords());
    }

    @PostMapping
    public void saveHotel(@RequestBody Hotel hotel){
        hotelService.save(hotel);
        //数据库保存了，就生产一条消息给MQ，ES随后会读MQ的这条消息同步更新ES索引库
        rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_INSERT_KEY, hotel.getId()); //只发送id过去就行了
    }

    @PutMapping()
    public void updateById(@RequestBody Hotel hotel){
        if (hotel.getId() == null) {
            throw new InvalidParameterException("id不能为空");
        }
        hotelService.updateById(hotel);
        //数据库更新了，就生产一条消息给MQ，ES随后会读MQ的这条消息同步更新ES索引库
        rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_INSERT_KEY, hotel.getId()); //只发送id过去就行了
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        hotelService.removeById(id);
        //数据库删除了，就生产一条消息给MQ，ES随后会读MQ的这条消息同步更新ES索引库
        rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_DELETE_KEY, id); //只发送id过去就行了
    }
}
