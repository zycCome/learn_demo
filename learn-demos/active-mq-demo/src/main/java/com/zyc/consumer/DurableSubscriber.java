package com.zyc.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
@Component
public class DurableSubscriber {

    @JmsListener(
            destination = "${activemq.topic.name}",
            containerFactory = "jmsListenerContainerFactory",
            subscription = "SHARED_SUBSCRIPTION" // 相同订阅名称
    )
    public void onMessage(String message) {
        System.out.printf("[%s] 收到消息: %s%n",
                LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME),
                message);
    }
}
