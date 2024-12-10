package com.zyc.learn_demo.concurrent;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 参考：https://juejin.cn/post/6844904020973191181
 * @author zyc66
 * @date 2024/12/10 15:33
 **/
public class DisruptorDemo {


    @Test
    public void first_demo() throws InterruptedException {
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );
        disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        eventProducer.onData(UUID.randomUUID().toString());

        Thread.sleep(10000);
    }


    @Test
    public void many_consumer_demo() throws InterruptedException {
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );
        disruptor.handleEventsWithWorkerPool(new OrderEventHandler("1"),new OrderEventHandler("2"));
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        eventProducer.onData(UUID.randomUUID().toString());
        eventProducer.onData(UUID.randomUUID().toString());
        eventProducer.onData(UUID.randomUUID().toString());
        eventProducer.onData(UUID.randomUUID().toString());
        eventProducer.onData(UUID.randomUUID().toString());
        eventProducer.onData(UUID.randomUUID().toString());

        Thread.sleep(10000);
    }


    @Test
    public void many_producer_demo() throws InterruptedException {
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                // 这里的枚举修改为多生产者
                ProducerType.MULTI,
                new YieldingWaitStrategy()
        );
        disruptor.handleEventsWithWorkerPool(new OrderEventHandler("zzz"), new OrderEventHandler("yyy"));
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        // 创建一个线程池，模拟多个生产者
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            int j = i;
            fixedThreadPool.execute(() -> eventProducer.onData(j+""));
        }

        Thread.sleep(600000);
    }
}
