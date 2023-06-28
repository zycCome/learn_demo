package com.javacode2018.lesson001.demo25.test7;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import({ BeanConfig2.class,BeanConfig1.class})
// 顺序会影响MyConfigurationCondition1的判断，必须要被判断的bean先被加载
@Import({ BeanConfig1.class,BeanConfig2.class})
public class MainConfig7 {
}
