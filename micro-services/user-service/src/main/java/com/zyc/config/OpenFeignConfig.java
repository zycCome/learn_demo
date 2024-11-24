package com.zyc.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 在启动类的注解@EnableFeignClients上指定
 * 局部生效就在@FeignClient中指定,不能加@Configuration注解
 **/
public class OpenFeignConfig {
    @Bean
    public Logger.Level feignLogLevel() {
        // 日志级别为BASIC
        return Logger.Level.FULL;
    }
}


