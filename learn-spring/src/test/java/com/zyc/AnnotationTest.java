package com.zyc;

import com.zyc.annotation.AppConfig;
import com.zyc.annotation.CustomerFactoryBean;
import com.zyc.annotation.UserService;
import com.zyc.annotation.UserService2;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
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

        /*
         * 容器內建依赖测试
         * 下面这样获取会报错.但是通过@Autowired获取是ok的！！
         */
//        BeanFactory bean = applicationContext.getBean(BeanFactory.class);

        UserService2 userService2 = applicationContext.getBean(UserService2.class);
        System.out.println(userService2);

        /*
         * 证明：FactoryBean 注册的名称
         */
        Object customerFactoryBean = applicationContext.getBean("customerFactoryBean");
        Object customerFactoryBean2 = applicationContext.getBean(UserService.class);
        // println true
        System.out.println(customerFactoryBean == customerFactoryBean2);

    }

}
