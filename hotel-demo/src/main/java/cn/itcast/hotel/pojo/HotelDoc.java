package cn.itcast.hotel.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/*
    Hotel是数据库的实体，HotelDoc是ES的实体
    ES的实体和数据库的实体不太一致，所以要用该类的构造方法，将Hotel复制过来
 */
public class HotelDoc {
    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String location;
    private String pic;
    private Object distance;
    private boolean isAD;

    //复制Hotel实体给HotelDoc
    public HotelDoc(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();
        this.score = hotel.getScore();
        this.brand = hotel.getBrand();
        this.city = hotel.getCity();
        this.starName = hotel.getStarName();
        this.business = hotel.getBusiness();
        //2021.10.22 debug：这里的拼接经纬度必须正确，逗号必须是英文，而且拼接的参数不能有空格，否则ES就会自动把经纬度类型转换成test类型！！！
        //2021.10.22 debug：这里的拼接经纬度必须正确，逗号必须是英文，而且拼接的参数不能有空格，否则ES就会自动把经纬度类型转换成test类型！！！
        //2021.10.22 debug：这里的拼接经纬度必须正确，逗号必须是英文，而且拼接的参数不能有空格，否则ES就会自动把经纬度类型转换成test类型！！！
        this.location = hotel.getLatitude() + "," + hotel.getLongitude();  //拼接经纬度（因为ES的实体格式长这样）
        this.pic = hotel.getPic();
    }

    //DEBUG:这里的判断是否是AD字段，不能用lombok自动生成，也不能用alt+insert的方法生成，因为布尔类型的自动生成会抽风，需要手写
    //DEBUG:这里的判断是否是AD字段，不能用lombok自动生成，也不能用alt+insert的方法生成，因为布尔类型的自动生成会抽风，需要手写
    //DEBUG:这里的判断是否是AD字段，不能用lombok自动生成，也不能用alt+insert的方法生成，因为布尔类型的自动生成会抽风，需要手写
    public boolean getIsAD() {
        return isAD;
    }

    public void setIsAD(boolean isAD) {
        this.isAD = isAD;
    }
}
