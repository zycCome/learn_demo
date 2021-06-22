package com.zyc.learn_demo.concurrent;

import java.util.concurrent.SynchronousQueue;

/**
 * @author zhuyc
 * @date 2021/06/16 06:49
 **/
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue queue = new SynchronousQueue(true);

        new Thread(() -> {
            try {
                queue.put(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();
        new Thread(() -> {
            try {
                queue.put(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1);

        }).start();
        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        Thread.sleep(1000000);

    }
}
