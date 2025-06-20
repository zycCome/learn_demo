package com.zyc;

import com.zyc.producer.TopicProducer2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhuyc
 * @date 2025/05/24
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


    @Autowired
    private TopicProducer2 producer;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        producer.sendMessages();
    }
}
