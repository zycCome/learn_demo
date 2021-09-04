package com.zyc;

import com.zyc.annotation.AppConfig;
import com.zyc.annotation.UserService;
import com.zyc.annotation.UserService2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhuyc
 * @date 2021/09/03 23:05
 **/
public class AnnotationTest {


    @Test
    public void test1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("容器启动完成");
        UserService2 userService2 = applicationContext.getBean(UserService2.class);
        System.out.println(userService2);
        Object customerFactoryBean = applicationContext.getBean("customerFactoryBean");
        System.out.println(customerFactoryBean);

    }

}
