package com.zyc.learn_demo.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuyc
 * @date 2021/06/10 06:46
 **/
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    // condition 依赖于 lock 来产生
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[3];
    int putptr, takeptr, count;

    // 生产
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            lock.lock();
            while (count == items.length)
                notFull.await();  // 队列已满，等待，直到 not full 才能继续生产
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal(); // 生产成功，队列已经 not empty 了，发个通知出去
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    // 消费
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            lock.lock();
            while (count == 0)
                notEmpty.await(); // 队列为空，等待，直到队列 not empty，才能继续消费
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal(); // 被我消费掉一个，队列 not full 了，发个通知出去
            lock.unlock();
            return x;
        } finally {
            lock.unlock();
        }
    }

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        BoundedBuffer buffer = new BoundedBuffer();
        int sleepSecond = 3;
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(sleepSecond * 1000));
                    buffer.put(i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(random.nextInt(sleepSecond * 1000));
                    System.out.println(buffer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });
        thread.start();
        thread.join();


    }
}
