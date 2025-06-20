package com.zyc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
public class TopicProducer {


    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "TEST_DURABLE_TOPIC";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = factory.createConnection();

        // 创建会话（非事务、自动ACK）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建生产者并设置持久化模式
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();

        // 发送10条测试消息
        for (int i = 1; i <= 10; i++) {
            TextMessage message = session.createTextMessage("MSG-" + i);
            producer.send(message);
            System.out.println("[生产者] 已发送: " + message.getText());
        }

        // 关闭连接
        session.close();
        connection.close();
    }

}
