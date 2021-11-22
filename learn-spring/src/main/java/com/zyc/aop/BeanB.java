package com.zyc.aop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @date 2021/09/05 17:47
 **/
//@Component
@Data
public class BeanB {

    public int type = 1;

    @Autowired
    private BeanA beanA;

    /**
     * 测试循环依赖
     * 结论：因为初始化顺序的问题，@Aysnc不是百分百出现错误
     */
//    @Async
    public void test1() {
        System.out.println(1);
    }

}
