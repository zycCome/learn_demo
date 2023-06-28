package com.zyc.demo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author zyc66
 * @date 2023/05/26 22:26
 **/
@SpringBootApplication
@EnableDubbo
@EnableScheduling
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Scheduled(initialDelay = 10000 ,fixedDelay = 30000)
    public void test() {

        // get result
        System.out.println(111);
    }
}

