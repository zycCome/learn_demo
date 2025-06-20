package com.zyc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
public class DurableSubscriber {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "TEST_DURABLE_TOPIC";
    private static final String CLIENT_ID = "FIXED_CLIENT_ID"; // 固定客户端ID
    private static final String SUB_NAME = "SAME_SUBSCRIPTION"; // 相同订阅名

    public static void startConsumer(String consumerName) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = factory.createConnection();

        // 设置固定ClientID（关键配置）
        connection.setClientID(CLIENT_ID);
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建持久订阅者
        MessageConsumer consumer = session.createDurableSubscriber(topic, SUB_NAME);

        // 异步接收消息
        consumer.setMessageListener(message -> {
            try {
                TextMessage textMessage = (TextMessage) message;
                System.out.printf("[%s] 收到消息: %s%n", consumerName, textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}
