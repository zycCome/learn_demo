package com.javacode2018.lesson001.demo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zyc66
 * @date 2024/12/25 23:36
 **/

@Component
public class BeanScopeModelService {

    @Autowired
    private BeanScopeModel beanScopeModel;

    public BeanScopeModelService() {
        System.out.println(String.format("线程:%s,create BeanScopeModelService,{this=%s}", Thread.currentThread(), this));
    }


    public void printBeanScopeModel() {
        System.out.println("printBeanScopeModel: "+beanScopeModel);
    }

}
