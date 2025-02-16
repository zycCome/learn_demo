package com.zyc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 该类会在spring项目启动后执行一些初始化动作
 *
 * 验证：该类的方法是在监听器监听服务 ContextRefreshedEvent 事件之后调用（只考虑默认的单线程场景），在ApplicationReadyEvent 事件之前
 **/
@Component
public class StartCommad implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("server started !");
    }
}
