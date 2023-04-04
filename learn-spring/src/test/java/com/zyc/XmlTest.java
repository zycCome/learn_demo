package com.zyc;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

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
        User user = (User)applicationContext.getBean("userFactory");
        User user2 = (User)applicationContext.getBean("userFactory");
        UserFactoryBean userFactoryBean= (UserFactoryBean)applicationContext.getBean("&userFactory");
        System.out.println(user == user2);
//        User user = applicationContext.getBean(User.class);
        applicationContext.getBean(UserFactoryBean.class);
        System.out.println("end");
    }


    @Test
    public void testBeanFactory() {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("bean1.xml"));
        System.out.println("容器启动完成");
        User user = (User)beanFactory.getBean("userFactory");
        System.out.println(user);
    }


    /**
     * 测试lazy-init为true+依赖的场景
     */
    @Test
    public void actualTimeDependencyLazyBean() {
        System.out.println("spring容器启动中...");
        String beanXml = "classpath:demo11/actualTimeDependencyLazyBean.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(beanXml); //启动spring容器
        System.out.println("spring容器启动完毕...");
    }


}
