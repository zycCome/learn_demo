package com.zyc.nacos.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费者
 *
 * @author zhuyc
 * @date 2021/7/16 16:45
 */
@RestController
@RequestMapping("order")
@RefreshScope //动态刷新配置，重要
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    //Spring常规取值
    @Value("${member.name:}")
    private String memberName;

    @Value("${member.age:}")
    private Integer memberAge;


    @Value("${db.message:}")
    private String dbMessage;

    @Value("${log.message:}")
    private String logMessage;

    int i = 0;

    @GetMapping("list")
    public String list() {
        System.out.println(i++);
        String result = restTemplate.getForObject("http://course-service/course/list", String.class);
        return result + "，memberName：" + memberName + "，memberAge：" + memberAge +" ,dbMessage: "+ dbMessage +" ,logMessage: "+logMessage;
    }


}
