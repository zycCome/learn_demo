package com.zyc.service.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.api.UserService;
import org.apache.dubbo.samples.po.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zyc66
 * @date 2024/11/18 13:24
 **/
@DubboService(weight = 100)
@Slf4j
public class UserServiceImpl implements UserService {


    @Value("${tag}")
    private String tag;

    @Override
    public User getByUserId(Integer userId) {
       log.info("{} getByUserId :{}", tag,userId);

        return new User(userId, RandomUtil.randomString(10));
    }

    @Override
    public User getByTag(String tag2) {
        log.info("{} getByTag :{}", tag,tag2);
        return new User(-1,tag);
    }

    @Override
    public String testDynamicConfig(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return tag;
    }

    @Override
    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
                response.onNext("hello,"+data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                response.onCompleted();
            }
        };
    }

}
