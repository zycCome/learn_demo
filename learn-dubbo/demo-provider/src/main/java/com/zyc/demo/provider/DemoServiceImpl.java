package com.zyc.demo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zyc.demo.api.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuyc
 * @date 2022/04/22 11:12
 **/
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
