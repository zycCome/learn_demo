package com.zyc.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhuyc
 * @date 2021/09/07 08:00
 **/
@Aspect // 表明这是一个切面类
public class DemoAspect {

    /**
     * 抽取公共的切点，本类以及其他切面类都可以使用
     */
    @Pointcut("execution(public void com.zyc.aop.Demo.printHello(..))")
    public void cut() {
    }

    /**
     * 前置通知
     */
    @Before("cut()")
    public void beforePrint() {
        System.out.println("开始执行");
    }

    /**
     * 后置通知
     */
    @After("cut()")
    public void afterPrint() {
        System.out.println("执行结束");
    }

}
