package com.zyc;

import com.zyc.aop.*;
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
        //按Demo类型，获取不到bean
//        DemoI demo2=applicationContext.getBean(Demo.class);
//        System.out.println(demo2);
        demo.printHello();

        //如果找到多个相同类型的组件，那么再将属性的名称作为组件的id，到IOC容器中进行查找
        BeanA beanA = applicationContext.getBean(BeanA.class);
        System.out.println(beanA.getRepeatedBeanB().getType());

        BeanC beanC = applicationContext.getBean(BeanC.class);
        System.out.println(beanC);


    }

}
