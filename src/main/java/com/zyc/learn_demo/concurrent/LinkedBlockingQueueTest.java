package com.zyc.learn_demo.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhuyc
 * @date 2021/06/14 19:54
 **/
public class LinkedBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingQueue queue = new LinkedBlockingQueue(1);
        queue.put(new Object());
        queue.put(new Object());
        queue.take();

    }

}
