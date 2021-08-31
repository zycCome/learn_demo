package com.zyc.learn_demo.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件锁测试
 *
 * @author zhuyc
 * @date 2021/08/29 07:30
 **/
public class ConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("before await");
                condition.await();
                System.out.println("after await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("before signal");
                condition.signal();
                System.out.println("after signal");
            } finally {
                lock.unlock();
            }

        }).start();

        Thread.sleep(2000);
    }


    /**
     * debug测试抛出中断异常是否需要先获取锁
     *
     * 结论：需要
     */
    @Test
    public void testThrowIENeedAcquire() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("before await");
                condition.await();
                System.out.println("after await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });
        thread.start();
        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("before signal");
                thread.interrupt();
                condition.signal();
                System.out.println("after signal");
            } finally {
                lock.unlock();
            }

        }).start();

        Thread.sleep(200*1000);

    }
}
