package com.javacode2018.configuration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zyc66
 * @date 2024/11/05 15:41
 **/
public class CaseTest {

    @Test
    public void annotationConfigTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfig.class);
        BeanConfig beanConfig = (BeanConfig) ac.getBean("beanConfig");
        // 即使通过 beanConfig 调用，也不会执行第二次
        Person lisi = beanConfig.lisi();
        System.out.println(lisi);
    }

}
