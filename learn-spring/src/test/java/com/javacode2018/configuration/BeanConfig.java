package com.javacode2018.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyc66
 * @date 2024/11/05 15:40
 **/
@Configuration
@ComponentScan
public class BeanConfig {

    @Bean(initMethod = "initial")
    public Person lisi() {
        System.out.println("lisi() called");
        return new Person("lisi", 20);
    }

    @Bean(value = "customName")
    public Person person() {
        // 测试 lisi() 在配置类拥有注解 @Configuration 时只会真正执行一次
        lisi();
        return new Person("wangwu", 30);
    }



}
