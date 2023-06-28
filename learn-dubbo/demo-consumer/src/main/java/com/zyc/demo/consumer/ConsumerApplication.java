package com.zyc.demo.consumer;

import com.zyc.demo.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author zyc66
 * @date 2023/05/26 22:29
 **/
@SpringBootApplication
@EnableDubbo
@EnableScheduling
public class ConsumerApplication {


    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @Scheduled(initialDelay = 10000 ,fixedDelay = 30000)
    public void test() {
        String hello = demoService.sayHello("world");
        // get result
        System.out.println(hello);
    }

}

