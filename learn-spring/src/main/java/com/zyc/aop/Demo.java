package com.zyc.aop;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuyc
 * @date 2021/09/07 08:00
 **/
public class Demo implements DemoI {

    /**
     * 测试动态代理选择规律
     * 结论：继承接口+方法 会用jdk动态代理。bean里面没有Demo类了
     */
    @Override
    @Transactional
    public void printHello() {
        System.out.println("hello");
    }

    public void printHello2() {
        System.out.println("hello2");
    }

}
