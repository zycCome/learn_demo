package com.zyc.boot.config;

import com.zyc.boot.pojo.po.DevelopTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyc66
 * @date 2024/11/05 10:54
 **/
// 在默认扫描的场景下，如果不加 @Configuration 注解，那么 @bean 修饰的方法会失效。前提，没有显示将当前类显示添加为配置类
//@Configuration
public class NormalConfig {

    @Bean
    public DevelopTask beanMethod1() {
        return new DevelopTask();
    }

}
