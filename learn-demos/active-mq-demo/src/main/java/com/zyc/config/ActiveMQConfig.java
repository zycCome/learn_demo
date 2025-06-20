package com.zyc.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import java.util.UUID;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setTargetConnectionFactory(activeMQConnectionFactory());
        factory.setClientId("FIXED_CLIENT_ID"); // 固定客户端ID[1](@ref)
        factory.setSessionCacheSize(10);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate(cachingConnectionFactory());
        template.setPubSubDomain(true); // 使用Topic模式
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory());
        factory.setSubscriptionDurable(true); // 启用持久订阅[6](@ref)
        factory.setPubSubDomain(true);
//        factory.setClientId("FIXED_CLIENT_ID");
        factory.setClientId("CLIENT_" + UUID.randomUUID()); // 动态唯一ID


        return factory;
    }
}
