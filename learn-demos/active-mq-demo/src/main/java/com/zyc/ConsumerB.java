package com.zyc;

import javax.jms.JMSException;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
public class ConsumerB {

    public static void main(String[] args) {
        try {
            DurableSubscriber.startConsumer("ConsumerB");
        } catch (JMSException e) {
            System.err.println("消费者B连接失败: " + e.getMessage());
        }
        while(true);
    }

}
