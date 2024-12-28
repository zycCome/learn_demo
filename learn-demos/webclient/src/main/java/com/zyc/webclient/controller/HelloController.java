package com.zyc.webclient.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Description TODO
 * @Author zilu
 * @Date 2022/6/27 8:02 PM
 * @Version 1.0.0
 **/
@RestController
public class HelloController {



    @GetMapping("/hello")
    public Mono<String> hello() {   // 【改】返回类型为Mono<String>
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Mono.just("Welcome to reactive world ~");     // 【改】使用Mono.just生成响应式数据
    }

    @GetMapping("/mvc")
    public String mvc() {
        return "mvc";
    }


    @PostMapping("/data")
    public String data(@RequestBody Person person) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(JSONUtil.toJsonStr(person));
        return "data";
    }

    @PostMapping("/abc/test")
    public String test(@RequestBody Map map) {
        System.out.println(JSONUtil.toJsonStr(map));
        return "data";
    }
}
