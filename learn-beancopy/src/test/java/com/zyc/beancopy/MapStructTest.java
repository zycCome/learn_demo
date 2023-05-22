package com.zyc.beancopy;

import com.zyc.beancopy.entity.UserEntity;
import com.zyc.beancopy.entity.UserEntity1;
import com.zyc.beancopy.entity.UserEntity2;
import com.zyc.beancopy.mapper.IPersonMapper;
import com.zyc.beancopy.mapper.IPersonMapper1;
import com.zyc.beancopy.mapper.IPersonMapper2;
import com.zyc.beancopy.po.UserPo;
import com.zyc.beancopy.po.UserPo2;
import org.junit.Test;

import java.util.Date;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/18 10:57 AM
 * @Version 1.0.0
 **/
public class MapStructTest {

    @Test
    public void testNormal() {
        System.out.println("-----------testNormal-----start------");
        UserPo userPo = UserPo.builder()
                .id(1L)
                .gmtCreate(new Date())
                .buyerId(666L)
                .userNick("测试mapstruct")
                .userVerified("ok")
                .age(18L)
                .build();
        System.out.println("1234" + userPo);
        UserEntity userEntity = IPersonMapper.INSTANCT.po2entity(userPo);
        // 发现是浅拷贝？
        System.out.println(userEntity);
        System.out.println("-----------testNormal-----ent------");
    }

    /**
     * 属性类型相同名称不同
     */
    @Test
    public void testNormal1() {
        System.out.println("-----------testNormal-----start------");
        UserPo userPo = UserPo.builder()
                .id(1L)
                .gmtCreate(new Date())
                .buyerId(666L)
                .userNick("测试mapstruct")
                .userVerified("ok")
                .age(18L)
                .build();
        System.out.println("1234" + userPo);
        UserEntity1 userEntity1 = IPersonMapper1.INSTANCT.po2entity(userPo);
        // 发现是浅拷贝？
        System.out.println(userEntity1);
        System.out.println("-----------testNormal-----ent------");
    }


    /**
     * 属性类型相同名称不同
     */
    @Test
    public void test2() {
        System.out.println("-----------testNormal-----start------");
        String attributes = "{\"id\":2,\"name\":\"测试123\"}";
        UserPo2 userPo = UserPo2.builder()
                .id(1L)
                .gmtCreate(new Date())
                .buyerId(666L)
                .userNick("测试mapstruct")
                .createTime("2023-05-18 09:00:00")
                .userVerified("ok")
                .age("18")
                .attributes(attributes)
                .build();
        System.out.println("1234" + userPo);
        UserEntity2 userEntity = IPersonMapper2.INSTANCT.po2entity(userPo);
        System.out.println(userEntity);
        System.out.println("-----------testNormal-----ent------");
    }

}
