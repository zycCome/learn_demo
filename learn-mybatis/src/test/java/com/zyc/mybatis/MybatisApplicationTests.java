package com.zyc.mybatis;

import com.zyc.mybatis.mapper.UserMapper;
import com.zyc.mybatis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhuyc
 * @date 2022/04/21 12:43
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        /**
         * spring整合mybatis后，sqlsession的创建时机：
         * Mapper里面持有一个SqlSessionTemplate对象，该对象实现了SqlSession接口。
         * 调用对应方法时，会交给内部SqlSessionInterceptor对象，该对象会尝试从Spring的事务管理器中获取当前线程已经持有的SqlSession。如果获取失败，则创建一个新的
         */
        List<User> list = userMapper.list();
        System.out.println(list);
    }

}
