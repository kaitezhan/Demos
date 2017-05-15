package com.boneix.test;

import com.boneix.netty.proto.RequestRichManProto;
import com.boneix.netty.proto.RichManProto;
import com.boneix.netty.proto.RichManProto.RichMan.Car;
import com.boneix.netty.proto.RichManProto.RichMan.CarType;
import com.boneix.netty.util.JsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzhang on 2017/5/15.
 */
public class DemoTests {


    public  void dothings(){
        String requestParamClassName="RequestRichManProto";
        String responseParamClassName="RichManProto";
        String packageName="com.boneix.netty";
        String packageSecondName="oa.userInfo";
        String methodName="findOneRichMan";

    }

    @Test
    public void test() {
        RequestRichManProto.RequestRichMan.Builder builder = RequestRichManProto.RequestRichMan.newBuilder();
        builder.setId(0);
        builder.setEmail("910818981@qq.com");
        builder.setName("沙发偶的");
        RichManProto.RichMan richMan = getRichMan(builder.build());
        System.out.println(richMan.toString());
    }


    private RichManProto.RichMan getRichMan(RequestRichManProto.RequestRichMan requestRichMan) {
        RichManProto.RichMan.Builder builder = RichManProto.RichMan.newBuilder();
        builder.setId(requestRichMan.getId());
        builder.setEmail(requestRichMan.getEmail());
        builder.setName(requestRichMan.getName());
        List<Car> cars = new ArrayList<Car>();
        Car car1 = Car.newBuilder().setName("上海大众超跑").setType(CarType.DASAUTO).build();
        Car car2 = Car.newBuilder().setName("Aventador").setType(CarType.LAMBORGHINI).build();
        Car car3 = Car.newBuilder().setName("奔驰SLS级AMG").setType(CarType.BENZ).build();

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        builder.addAllCars(cars);
        return builder.build();

    }
}
