package com.zyc.demo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zyc.demo.api.DemoService;
import com.zyc.demo.api.DemoService2;
import com.zyc.demo.api.User;
import org.apache.dubbo.config.annotation.DubboService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuyc
 * @date 2022/04/22 11:12
 **/

@DubboService
public class DemoServiceImpl2 implements DemoService2 {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        System.out.println(RpcContext.getContext().getAttachment("author"));
        System.out.println(RpcContext.getContext().getAttachment("author2"));

        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public String sayHello2(Integer age) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + age + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        System.out.println(RpcContext.getContext().getAttachment("author"));
        return "(age)Hello " + age + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public User sayHello2(User a) {
        User user = new User();
        user.setAge(11111);
        user.setName("test");
        return user;
    }
}
