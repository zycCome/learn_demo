package com.zyc.beancopy;

import com.zyc.beancopy.entity.UserEntity;
import com.zyc.beancopy.entity.UserEntity1;
import com.zyc.beancopy.entity.UserEntity2;
import com.zyc.beancopy.mapper.IPersonMapper;
import com.zyc.beancopy.mapper.IPersonMapper1;
import com.zyc.beancopy.mapper.IPersonMapper2;
import com.zyc.beancopy.po.Address;
import com.zyc.beancopy.po.NestedObject;
import com.zyc.beancopy.po.UserPo;
import com.zyc.beancopy.po.UserPo2;
import org.junit.Test;

import java.util.ArrayList;
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

        Address address = new Address();
        address.setName("杭州");
        address.setAge(122);
        userPo.setAddress(address);
        userPo.setIntegerList(new ArrayList<>());
        userPo.getIntegerList().add(1);
        userPo.getIntegerList().add(2);

        NestedObject nestedObject = new NestedObject();
        nestedObject.setName("嵌套对象");
        nestedObject.setAge(1);
        userPo.setNestedObject(nestedObject);

        UserEntity userEntity = IPersonMapper.INSTANCT.po2entity(userPo);
        // 发现是浅拷贝？ 1. String Integer Date 是浅拷贝 2. List 是深拷贝，但List中的元素是浅拷贝
        // 3. 对于嵌套对象，如果源对象和目标对象中的字段类型相同，并且它们是自定义引用类型，MapStruct 默认会直接传递引用，而不是创建新实例。
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
