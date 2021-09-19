package com.zyc;

import com.zyc.aop.Demo;
import com.zyc.aop.DemoI;
import com.zyc.aop.MyConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 切面测试
 *
 * @author zhuyc
 * @date 2021/09/07 08:02
 **/
public class AopTest {

    @Test
    public void test(){
        //创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);
        //获取容器中的对象
        DemoI demo=applicationContext.getBean(DemoI.class);
        demo.printHello();
    }

}
