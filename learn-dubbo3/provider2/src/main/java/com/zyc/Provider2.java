package com.zyc;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableDubbo
@SpringBootApplication
@ImportResource(locations = "classpath:provider.xml")// 测试混合使用
public class Provider2 {
    public static void main(String[] args) {
        SpringApplication.run(Provider2.class, args);
    }
}