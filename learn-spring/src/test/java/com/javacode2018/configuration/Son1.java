package com.javacode2018.configuration;

import com.javacode2018.lesson001.demo24.Service24;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @author zyc66
 * @date 2023/04/04 20:55
 **/
@Component
// 测试递归引入,会递归
@ComponentScan("com.javacode2018.lesson001.demo25.test8")
@Import(value = {Service24.class,MyImportBeanDefinitionRegistrar.class})
public class Son1 {

    private String name = "son1";
}
