package com.zyc.liteflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 2:22 PM
 * @Version 1.0.0
 **/
@SpringBootApplication
//把你定义的组件扫入Spring上下文中
@ComponentScan({"com.zyc.liteflow"})
public class LiteflowExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteflowExampleApplication.class, args);
    }
}

