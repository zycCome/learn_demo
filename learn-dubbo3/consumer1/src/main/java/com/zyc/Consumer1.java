package com.zyc;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class Consumer1 {
    public static void main(String[] args) {
        SpringApplication.run(Consumer1.class, args);
    }
}