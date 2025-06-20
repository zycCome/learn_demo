package com.zyc.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer2 {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${activemq.topic.name}")
    private String topicName;

    public void sendMessages() {
        for(int i=1; i<=5; i++) {
            jmsTemplate.convertAndSend(topicName, "MSG-"+i);
        }
    }
}

