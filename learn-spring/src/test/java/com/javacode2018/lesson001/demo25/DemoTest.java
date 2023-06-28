package com.javacode2018.lesson001.demo25;

import com.javacode2018.lesson001.demo25.test7.MainConfig7;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zyc66
 * @date 2023/04/04 19:58
 **/
public class DemoTest {

    @Test
    public void test7() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
        System.out.println("--------------");
        context.getBeansOfType(String.class).forEach((beanName, bean) -> {
            System.out.println(String.format("%s->%s", beanName, bean));
        });
    }

}
