package com.zyc.controller;

import com.alibaba.dubbo.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.api.DeptService;
import org.apache.dubbo.samples.api.UserService;
import org.apache.dubbo.samples.po.Dept;
import org.apache.dubbo.samples.po.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author zyc66
 * @date 2024/11/18 13:35
 **/
@RestController
@RequestMapping("/api/demo/dubbo")
@Slf4j
public class UserController {

    @Value("${tag}")
    private String tag;


    @DubboReference
    UserService userService;

    @GetMapping("/user/{userId}")
    public User getByUserId(@PathVariable("userId")Integer userId) {
        log.info("{} getByUserId :{}",tag,userId);
        return userService.getByUserId(userId);
    }

    @GetMapping("/userTag/{tag}")
    public User getByTag(@PathVariable("tag")String tag) {
        log.info("before tag:{}",RpcContext.getContext().getAttachment(Constants.TAG_KEY));
        log.info("{} getByTag :{}",tag,tag);
        if("null".equals(tag)) {
            RpcContext.getContext().setAttachment(Constants.TAG_KEY, null);
        } else {
            RpcContext.getContext().setAttachment(Constants.TAG_KEY, tag);
        }
        log.info("after tag:{}",RpcContext.getContext().getAttachment(Constants.TAG_KEY));
        User byTag = userService.getByTag(tag);
        log.info("complete tag:{}",RpcContext.getContext().getAttachment(Constants.TAG_KEY));

        return byTag;
    }

    @GetMapping("/userSleep/{sleepTime}")
    public String userSleep(@PathVariable(value = "sleepTime",required = false)Integer sleepTime) {
        log.info("{} userSleep :{}",tag,sleepTime);
        return userService.testDynamicConfig(sleepTime);
    }


    @GetMapping("/userStream")
    public String userStream() {
        log.info("userStream");
        StreamObserver<String> request = userService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });
        for (int i = 0; i < 5; i++) {
            request.onNext("stream request" + i);
        }
        request.onCompleted();
        return "end";
    }
}
