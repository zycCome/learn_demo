package com.javacode2018.lesson001.demo25.test7;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

@Configuration
//@Conditional(MyCondition1.class)
@ComponentScan
@Order(1)
@Conditional(MyConfigurationCondition1.class)
@ConditionalOnBean(Service.class)
public class BeanConfig2 {
    @Bean
    public String name() {
        return "路人甲Java";
    }
}
