package com.zyc.aop;

import org.aspectj.lang.annotation.*;

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
     * 抽取公共的切点，本类以及其他切面类都可以使用
     */
    @Pointcut("execution(public void com.zyc.aop.Demo.printHello2(..))")
    public void cut3() {
    }

    /**
     * 用于测试 创建代理时，是否是用所有advisor来遍历的
     */
    @Pointcut("execution(public void com.zyc.aop.BeanA.test1())")
    public void cut2() {
    }

    /**
     * 前置通知
     */
    @Before("cut()")
    public void beforePrint() {
        System.out.println("开始执行");
    }

    /**
     * 前置通知
     */
    @Before("cut3()")
    public void beforePrint3() {
        System.out.println("测试开始执行");
    }

    /**
     * 后置通知
     */
    @After("cut()")
    public void afterPrint() {
        System.out.println("执行结束");
    }

    @After("cut2()")
    public void afterPrint2() {
        System.out.println("执行结束");
    }

    // 在目标方法（即div方法）结束时被调用
    // @After("pointCut()")
    @After("cut()")
    public void logEnd() {
        System.out.println("结束......@After");
    }

    // 在目标方法（即div方法）正常返回了，有返回值，被调用
    @AfterReturning("cut()")
    public void logReturn() {
        System.out.println("正常返回......@AfterReturning，运行结果是：{}");
    }

    // 在目标方法（即div方法）出现异常，被调用
    @AfterThrowing("cut()")
    public void logException() {
        System.out.println("出现异常......异常信息：{}");
    }


}
