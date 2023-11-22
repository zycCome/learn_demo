package com.zyc.learn_demo.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * @Description
 * @Author zilu
 * @Date 2023/11/8 15:14
 * @Version 1.0.0
 **/
public class LockInterruptTest {

    private Lock lock = new ReentrantLock();

    public void doBussiness() {
        String name = Thread.currentThread().getName();

        try {
            System.out.println(name + " 开始获取锁");
//            lock.lockInterruptibly();
            lock.lock();
            System.out.println(name + " 得到锁，线程状态:"+Thread.currentThread().isInterrupted());
            System.out.println(name + " 开工干活");
            for (int i=0; i<5; i++) {
                // 是sleep导致lock()后抛出了异常
                Thread.sleep(1000);
                System.out.println(name + " : " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " 被中断");
            System.out.println(name + " 做些别的事情");
        } finally {
            try {
                lock.unlock();
                System.out.println(name + " 释放锁");
            } catch (Exception e) {
                System.out.println(name + " : 没有得到锁的线程运行结束");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        LockInterruptTest lockTest = new LockInterruptTest();

        Thread t0 = new Thread(
                new Runnable() {
                    public void run() {
                        lockTest.doBussiness();
                    }
                }
        );

        Thread t1 = new Thread(
                new Runnable() {
                    public void run() {
                        lockTest.doBussiness();
                    }
                }
        );

        // 启动线程t1
        t0.start();
        Thread.sleep(10);
        // 启动线程t2
        t1.start();
        Thread.sleep(100);
        // 线程t1没有得到锁，中断t1的等待
        t1.interrupt();
    }
}