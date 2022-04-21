package com.zyc;

import com.zyc.circle.contructor.A;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhuyc
 * @date 2022/04/18 11:39
 **/
public class CircleTest {


    /**
     * 测试构造器循环依赖
     * 结果：报错
     */
    @Test
    public void test4() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("circle-constructor.xml");
        A a = ctx.getBean(A.class);
        ctx.close();
    }


    /**
     * 测试setter循环依赖
     */
    @Test
    public void test5() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("circle-setter.xml");
        com.zyc.circle.setter.A a = ctx.getBean(com.zyc.circle.setter.A.class);
        ctx.close();
    }


    /**
     * 测试构造+setter循环依赖
     *
     * A创建完会放到三级缓存因此没有问题。
     * -----
     *
     * 如果 A增加了 depends-on="B" 属性，则还是会抛出异常。因此B没有放入三级缓存中
     */
    @Test
    public void tes6() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("circle-mix.xml");
        System.out.println("mix");
        ctx.close();
    }
}
