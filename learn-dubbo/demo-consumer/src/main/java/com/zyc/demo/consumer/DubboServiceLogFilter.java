package com.zyc.demo.consumer;

import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// 可以使用注解也可以使用xml格式配置
// @Activate
@Activate(group={ Constants.CONSUMER }) // group表示只对消费者生效
public class DubboServiceLogFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(DubboServiceLogFilter.class);
    private final static String msg = ">>>>>线程ID:{%s},登录用户信息:{%s}\r\n>>>>>请求参数:{%s}\r\n>>>>>请求方法:{%s}\r\n>>>>>请求时长:{%s毫秒},请求结果:{%s}";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 方法开始执行的时间
        long startTime = System.currentTimeMillis();

        // 全部请求参数
        Object[] objects = invocation.getArguments();
        // 登录用户信息
        Object loginUser = null;
        // 请求参数
        Object[] paramObjects = null;

        if (objects != null && objects.length > 0){
            loginUser = objects[0];
            paramObjects = new Object[objects.length-1];
            // 除了数组objects第一位，将其他参数赋值到paramObjects
            System.arraycopy(objects, 1, paramObjects, 0, paramObjects.length);
        }

        String classMethod = invoker.getInterface() + "." + invocation.getMethodName();
        // 返回结果
        Result result = null;
        // 线程ID
        Long threadId = Thread.currentThread().getId();
        // 方法结束执行的时间
        long endTime;

        try {
            result = invoker.invoke(invocation);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            e.printStackTrace();
            throw e;
        }
        endTime = System.currentTimeMillis();
        return result;
    }
}

