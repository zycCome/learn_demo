package com.zyc.config;

import com.zyc.service.ATiger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 **/
@Configuration()
@Conditional(ConfigurationConditionA.class)
@Import(ConfigB.class)
public class ConfigA {


    @Bean
    public ATiger aTiger() {
        return new ATiger();
    }

}
