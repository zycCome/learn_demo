package com.zyc;

import javax.jms.JMSException;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
public class ConsumerA {

    public static void main(String[] args) throws JMSException {
        DurableSubscriber.startConsumer("ConsumerA");
        // 保持连接不关闭
        while(true);
    }

}
