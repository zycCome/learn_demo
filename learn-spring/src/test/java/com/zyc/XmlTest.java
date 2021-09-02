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
        User user = (User)applicationContext.getBean("user");
        System.out.println(user);
    }


}
