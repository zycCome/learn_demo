package com.zyc.learn_demo.concurrent;

import com.lmax.disruptor.RingBuffer;

/**
 * @author zyc66
 * @date 2024/12/10 15:29
 **/
public class OrderEventProducer {
    private final RingBuffer<OrderEvent> ringBuffer;
    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    public void onData(String orderId) {
        long sequence = ringBuffer.next();
        if(orderId.equals("23")) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            OrderEvent orderEvent = ringBuffer.get(sequence);
            orderEvent.setId(orderId);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

