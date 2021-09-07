package com.zyc.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 主配置类
 *
 * @author zhuyc
 * @date 2021/09/07 08:01
 **/
@EnableAspectJAutoProxy // 开启自动代理;要开启Spring的AOP，需要在配置主配置类上加上注解
@Configuration
public class MyConfigOfAOP {
    /**
     * 被代理类加入容器
     * @return
     */
    @Bean
    public Demo demo(){
        return new Demo();
    }

    /**
     * 切面类加入容器
     * @return
     */
    @Bean
    public DemoAspect demoAspect(){
        return new DemoAspect();
    }
}
