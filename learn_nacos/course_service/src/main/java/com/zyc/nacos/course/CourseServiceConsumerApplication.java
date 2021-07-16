package com.zyc.nacos.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author zhuyc
 * @date 2021/7/16 16:05
 */
@SpringBootApplication
@EnableDiscoveryClient //重点
public class CourseServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseServiceConsumerApplication.class, args);
    }
}

