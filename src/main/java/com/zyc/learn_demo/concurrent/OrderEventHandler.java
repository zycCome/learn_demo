package com.zyc.learn_demo.concurrent;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author zyc66
 * @date 2024/12/10 15:32
 **/
@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {


    public OrderEventHandler(String id) {
        this.id = id;
    }

    public OrderEventHandler() {
    }

    private String id;

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        log.info("id :{} ,event: {}, sequence: {}, endOfBatch: {}", id, event, sequence, endOfBatch);
    }
    @Override
    public void onEvent(OrderEvent event) {
        log.info("id: {},event: {}", id , event);
    }
}

