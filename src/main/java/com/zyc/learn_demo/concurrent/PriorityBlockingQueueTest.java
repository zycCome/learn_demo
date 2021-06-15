package com.zyc.learn_demo.concurrent;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author zhuyc
 * @date 2021/06/14 19:54
 **/
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {

        PriorityBlockingQueue queue = new PriorityBlockingQueue();
        queue.offer(new Object());
        queue.offer(new Object());

    }

}
