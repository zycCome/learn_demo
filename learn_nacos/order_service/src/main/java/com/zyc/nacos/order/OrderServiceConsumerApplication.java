package com.zyc.nacos.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 *
 * @author zhuyc
 * @date 2021/7/16 16:05
 */
@SpringBootApplication
@EnableDiscoveryClient //重点
public class OrderServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceConsumerApplication.class, args);
    }

    /**
     * 新增restTemplate对象注入方法，注意，此处LoadBalanced注解一定要加上，否则无法远程调用
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

