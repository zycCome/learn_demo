package com.zyc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.api.Greeter;
import org.apache.dubbo.samples.api.GreeterReply;
import org.apache.dubbo.samples.api.GreeterRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyc66
 * @date 2024/11/19 12:12
 **/
@RestController
@RequestMapping("/api/demo/greeter")
@Slf4j
public class GreeterController {

    @DubboReference
    private Greeter greeter;


    @GetMapping("/greet")
    public String greet() {
        log.info("greet");
        GreeterReply name = greeter.greet(GreeterRequest.newBuilder().setName("name").build());
        return "greet end";

    }

}
