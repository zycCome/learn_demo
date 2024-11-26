package com.zyc.service;

import com.zyc.entity.User;
import io.opentracing.Span;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;
import org.apache.skywalking.apm.toolkit.trace.*;
import org.springframework.stereotype.Service;

/**
 * @author zyc66
 * @date 2024/11/25 11:15
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService{



    @Trace
    @Tag(key = "username", value = "arg[0]") //固定写法必须写成 arg[0]
    @Tag(key = "password", value = "arg[1]")
    @Tag(key = "result.orderId", value = "returnedObj.id") //固定写法 必须写成returnedObj.XXXX
    @Tag(key = "result.email", value = "returnedObj.email")
    @Tag(key = "result", value = "returnedObj")
    @Override
    public User checkUser(String username, String password) {
        log.info("username:{},password:{}",username,password);

        User user = new User();
        user.setId(8848L);
        return user;
    }

    @Override
    public boolean postUser(String username, String password) {
        //ActiveSpan.setOperationName("编码修改span名称");// 这是为当前所在的span修改名字的操作
        try {
            String traceContext = TraceContext.traceId();
            log.info("traceContext:{}",traceContext);
            SpanRef span = Tracer.createLocalSpan("编码新增span");
            ActiveSpan.tag("my_tag", "my_value");
            ActiveSpan.error();
            ActiveSpan.error("Test-Error-Reason");

            ActiveSpan.error(new RuntimeException("Test-Error-Throwable"));
            ActiveSpan.info("Test-Info-Msg");
            ActiveSpan.debug("Test-debug-Msg");
        } finally {
            Tracer.stopSpan();
        }
        return false;
    }


    @Override
    public void openTrace() {
        SkywalkingTracer tracer = new SkywalkingTracer();
        io.opentracing.Tracer.SpanBuilder spanBuilder = tracer.buildSpan("/order-service/method-getOrderWithOpentracing");
        Span span = spanBuilder.withTag("tag1", false).withTag("tag2", "这是tag2").startManual();

        System.out.println("-----1------");
        span.finish();
    }

    @Override
    public void api1() {
        log.info("api11111111");
    }

}
