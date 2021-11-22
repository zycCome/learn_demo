package com.zyc.aop;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 循环依赖测试
 *
 * @author zhuyc
 * @date 2021/09/05 17:46
 **/
@Component
//DependsOn也能处理循环依赖
//@DependsOn("repeatedBeanB")
@Data
public class BeanA {

    @Autowired
    public BeanA(DemoI demoI,DependenceDemo dependenceDemo) {
        System.out.println("BeanA 有2参构造器");
    }

//    @Autowired
//    public BeanA(DemoI demoI) {
//        System.out.println("BeanA 有1参构造器");
//    }

    public BeanA() {
        System.out.println("BeanA 无参构造器");

    }

    @Autowired
    private BeanB repeatedBeanB;

//    /**
//     * 测试循环依赖
//     */
    @Async
    public void test1() {
        System.out.println(1);
    }

}
