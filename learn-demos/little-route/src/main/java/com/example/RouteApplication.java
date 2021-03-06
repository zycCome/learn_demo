package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 理由启动类
 *
 * @author zhuyc
 * @date 2021/10/15 15:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RouteApplication {


    public static void main(String[] args) {
        SpringApplication.run(RouteApplication.class, args);
    }

    /**
     * 新增restTemplate对象注入方法，注意，此处LoadBalanced注解一定要加上，否则无法远程调用
     */
    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate() {
        //配置HTTP超时时间
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(6000);
        httpRequestFactory.setConnectTimeout(6000);
        httpRequestFactory.setReadTimeout(6000);
        return new RestTemplate(httpRequestFactory);
    }
}
