package com.javacode2018.lesson001.demo25.test7;

import com.javacode2018.lesson001.demo24.BeanConfig3;
import org.springframework.context.annotation.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

//@Configuration
@Order(2)
@Import(BeanConfig3.class)
public class BeanConfig1 {
    @Bean
    public Service service() {
        return new Service();
    }

    @Bean
    public BeanConfig3 beanConfig3() {
        return new BeanConfig3();
    }
}
