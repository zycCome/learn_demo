package com.zyc.annotation;

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
public class BeanA {

    @Autowired
    private BeanB beanB;

//    /**
//     * 测试循环依赖
//     */
//    @Async
    public void test1() {
        System.out.println(1);
    }

}
