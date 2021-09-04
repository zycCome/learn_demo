package com.zyc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml形式配置spring 测试
 *
 * @author zhuyc
 * @date 2021/09/02 08:06
 **/
public class XmlTest {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/bean1.xml");
        System.out.println("容器启动完成");
//        User user = (User)applicationContext.getBean("user-alias");
        User user = applicationContext.getBean(User.class);
        applicationContext.getBean(UserFactoryBean.class);
        System.out.println("end");
    }


}
