package com.zyc.demo.api;

/**
 * 公共接口
 *
 * @author zhuyc
 * @date 2022/04/22 11:09
 **/
public interface DemoService {

    String sayHello(String name);
    String sayHello2(Integer age);
    User sayHello2(User a);

}
