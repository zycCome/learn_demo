package com.javacode2018.lesson001.demo24;

import com.javacode2018.lesson001.demo25.test7.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class BeanConfig3 {
    @Bean
    public Service24 service24() {
        return new Service24();
    }
}
