package com.zyc.annotation;

import com.zyc.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan("com.zyc")
@EnableAsync
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean(name = "user2")
    public User user() {
        User u2 = new User();
        u2.setAge("19");
        return u2;
    }

}

