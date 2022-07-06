package com.zyc.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.SendResult;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @Description 原生kafka生产者
 * @Author zilu
 * @Date 2022/6/21 1:54 PM
 * @Version 1.0.0
 **/
public class ProducerTest {

    @Test
    public void test1() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "192.168.4.96:9092");
        kafkaProps.put("producer.type", "async");
        kafkaProps.put("compression.type", "gzip");
        kafkaProps.put("retries", "3");
        kafkaProps.put("linger.ms", "100");
        //向kafka集群发送消息,除了消息值本身,还包括key信息,key信息用于消息在partition之间均匀分布。
        //发送消息的key,类型为String,使用String类型的序列化器
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        //发送消息的value,类型为String,使用String类型的序列化器
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //创建一个KafkaProducer对象，传入上面创建的Properties对象
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(kafkaProps);
        /**
         * 使用ProducerRecord<String, String>(String topic, String key, String value)构造函数创建消息对象
         * 构造函数接受三个参数：
         * topic--告诉kafkaProducer消息发送到哪个topic;
         * key--告诉kafkaProducer，所发送消息的key值，注意：key值类型需与前面设置的key.serializer值匹配
         * value--告诉kafkaProducer，所发送消息的value值，即消息内容。注意：value值类型需与前面设置的value.serializer值匹配
         */
        try {
            ProducerRecord producerRecord = new ProducerRecord("NOTICE_BROADCAST_TOPIC", 1, "{\"appId\":22222,\"createTime\":1655888288947,\"linkTime\":1655888288947,\"messageId\":296517,\"noticeMessage\":\"2B67D6BCA2435EEE07C3\",\"noticeNum\":0,\"noticeQoc\":0,\"noticeTime\":1655888462647,\"noticeType\":\"editOrganize\",\"organizeCode\":\"2B67D6BCA2435EEE07C3\",\"organizeId\":548,\"priority\":0,\"routeKey\":548,\"sendTime\":1655888288947,\"spenId\":0,\"status\":1,\"userId\":11111}");
            Future<SendResult<Integer, String>> listenableFuture = producer.send(producerRecord);
            boolean isResult = true;
            if (isResult) {
                // 同步发送
                listenableFuture.get();
//                listenableFuture.addCallback((SuccessCallback) -> {
//                    System.out.println("success");
//                }, (FailureCallback) -> {
//                    FailureCallback.printStackTrace();
//                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
