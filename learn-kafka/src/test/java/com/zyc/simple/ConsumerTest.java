package com.zyc.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author zyc66
 * @date 2024/11/10 16:34
 **/
public class ConsumerTest {


    @Test
    public void test1() {
        Properties props = new Properties();
        // kafka集群所需的broker地址
        props.put("bootstrap.servers", "172.20.45.45:9092");
        // kafka消费者群组名称
        props.put("group.id", "group_demo2");
        // 消费者从broker端获取的消息格式都是byte[]数组类型，key和value需要进行反序列化。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");
        props.put("max.poll.records","1");
        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("topic-zyc","topic-zyc-1"));

        try {
            for (int i = 0; i < 3; i++) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: key = %s, value = %s, partition = %s, offset = %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                }
                // 手动提交位移
                consumer.commitSync();
            }
        } finally {
            // 关闭消费者
            consumer.close();
        }

    }

}
