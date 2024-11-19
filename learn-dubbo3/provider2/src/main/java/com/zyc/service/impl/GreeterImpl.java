package com.zyc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.api.DubboGreeterTriple;
import org.apache.dubbo.samples.api.GreeterReply;
import org.apache.dubbo.samples.api.GreeterRequest;
import org.apache.dubbo.samples.api.UserService;

/**
 * @author zyc66
 * @date 2024/11/19 11:03
 **/
@Slf4j
@DubboService
public class GreeterImpl extends DubboGreeterTriple.GreeterImplBase {


    @Override
    public GreeterReply greet(GreeterRequest request) {
        log.info("Server {} received greet request {}", SERVICE_NAME, request);
        return GreeterReply.newBuilder()
                .setMessage("hello," + request.getName())
                .build();
    }

}
