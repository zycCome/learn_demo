package com.zyc.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试能否获取到Demo（非接口DemoI）
 *
 * @author zhuyc
 * @date 2021/09/08 08:16
 **/
@Component
public class DependenceDemo {

//    public DependenceDemo(@Autowired Demo demo) {
    // 只能获取到接口
    public DependenceDemo(@Autowired DemoI demo) {
//        demo.printHello();
    }
}
