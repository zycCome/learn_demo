package com.zyc.simple;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.SendResult;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
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
        kafkaProps.put("bootstrap.servers", "172.20.45.45:9092");
        kafkaProps.put("producer.type", "async");
        kafkaProps.put("compression.type", "gzip");
//        kafkaProps.put("retries", "3");
        kafkaProps.put("linger.ms", "100");
        //向kafka集群发送消息,除了消息值本身,还包括key信息,key信息用于消息在partition之间均匀分布。
        //发送消息的key,类型为String,使用String类型的序列化器
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        //发送消息的value,类型为String,使用String类型的序列化器
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //创建一个KafkaProducer对象，传入上面创建的Properties对象
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(kafkaProps);

        try {
            for (int i = 0; i < 5; i++) {
                sendMessage(producer,"topic-zyc-1",1,"{\"age\":"+i+"}");
            }

              Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }

    public void sendMessage( KafkaProducer<Integer, String> producer,String topic,Integer key, String value) throws ExecutionException, InterruptedException {
        ProducerRecord producerRecord = new ProducerRecord(topic,key,value);
        /**
         * 使用ProducerRecord<String, String>(String topic, String key, String value)构造函数创建消息对象
         * 构造函数接受三个参数：
         * topic--告诉kafkaProducer消息发送到哪个topic;
         * key--告诉kafkaProducer，所发送消息的key值，注意：key值类型需与前面设置的key.serializer值匹配
         * value--告诉kafkaProducer，所发送消息的value值，即消息内容。注意：value值类型需与前面设置的value.serializer值匹配
         */
        boolean isResult = false;
        if (isResult) {
            // 同步发送
            Future<SendResult<Integer, String>> listenableFuture = producer.send(producerRecord);
            listenableFuture.get();
        } else {
            producer.send(producerRecord,new Callback() {

                /**
                 * 当设置了retries参数后，回调是每次失败都调用，还是最后一次失败时调用？结果：最后一次失败时调用
                 * retries默认值时，会一直重试吗？使用iptables模拟断网环境。结果：不会一直重试
                 * @param metadata
                 * @param exception
                 */
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("Sent message: " + producerRecord + ", offset: " + metadata.offset());
                    } else {
                        // 这里重新发送消息
                        // 或者记录日志
                        System.out.println("Sent message error:"+ exception.getMessage());
                        exception.printStackTrace();
                    }
                }
            });
        }
    }

}
