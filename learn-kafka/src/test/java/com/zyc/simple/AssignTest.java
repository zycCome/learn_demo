package com.zyc.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.SendResult;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @Description 原生kafka生产者
 * @Author zilu
 * @Date 2022/6/21 1:54 PM
 * @Version 1.0.0
 **/
public class AssignTest {


    /**
     * 测试kafka的assign特性
     * 步骤：
     * 往主题添加3条消息，
     */
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
            ProducerRecord producerRecord = new ProducerRecord("ASSIGN_TEST", 1, "{\"type\":1,\"deviceSn\":\"84E0F42710011501\",\"timeStamp\":1672276299001,\"admitCode\":\"07D5220906130924W21FVSDS\",\"organizeId\":702,\"userId\":2005,\"appId\":20050055,\"organizeCode\":\"07D52D551B9C724C0895\"}");
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


    /**
     * 参考：https://www.cnblogs.com/liufei1983/p/10099804.html
     * 测试上面产生的3条消息能否读取到？能。取决于auto.offset.reset。并且发现消费组其实是有用的，kafka会记录offset。和chatGPT说的不符，说明不能完全信啊！
     * 停止test2后，再产生3条消息，再次重启test2，能否读到？能读取到中间没消费的值.不能，因为他们公用同一份offset记录，因此只要任意一个消费者推进了，就会推进offset
     */
    @Test
    public void test2() {
        Properties props = new Properties();
        // kafka集群所需的broker地址
        props.put("bootstrap.servers", "192.168.4.96:9092");
                // kafka消费者群组名称
                props.put("group.id", "group_demo3");
        // 消费者从broker端获取的消息格式都是byte[]数组类型，key和value需要进行反序列化。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");
        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.assign(Arrays.asList(new TopicPartition("ASSIGN_TEST", 0)));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
//                if(records.isEmpty()) {
//                    break;
//                }
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


    /**
     * 1.测试同一个消费组启动两个消费者，能否都收到消息？都收到！
     * 2.那如果两个消费者挂了一个B，在挂的期间，产生的消息被活着的A消费者消费了。B重启后，是不是消费不到期间产生的消息？
     * 3.assign中自动提交无效？有效！！！ 关闭自动提交，offset不变了；开启后，offset就会推进
     */
    @Test
    public void test3() {
        Properties props = new Properties();
        // kafka集群所需的broker地址
        props.put("bootstrap.servers", "192.168.4.96:9092");
        // kafka消费者群组名称
        props.put("group.id", "group_demo3");
        // 消费者从broker端获取的消息格式都是byte[]数组类型，key和value需要进行反序列化。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.assign(Arrays.asList(new TopicPartition("ASSIGN_TEST", 0)));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
//                if(records.isEmpty()) {
//                    break;
//                }
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("2Received message: key = %s, value = %s, partition = %s, offset = %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                }

                // 手动提交位移
//                consumer.commitSync();
            }
        } finally {
            // 关闭消费者
            consumer.close();
        }

    }

}
