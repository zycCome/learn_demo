package com.zyc.config;

import com.zyc.service.BTiger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 *
 **/
@Conditional(ConfigurationConditionB.class)
public class ConfigB {


    @Bean
    public BTiger bTiger() {
        return new BTiger();
    }

}
