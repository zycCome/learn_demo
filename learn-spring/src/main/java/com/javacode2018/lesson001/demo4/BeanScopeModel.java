package com.javacode2018.lesson001.demo4;

/**
 * @author zyc66
 * @date 2024/12/25 23:36
 **/
public class BeanScopeModel {

    public BeanScopeModel(String beanScope) {
        System.out.println(String.format("线程:%s,create BeanScopeModel,{sope=%s},{this=%s}", Thread.currentThread(), beanScope, this));
    }

}
