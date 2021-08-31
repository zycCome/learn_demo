package com.zyc.learn_demo.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuyc
 * @date 2021/06/09 07:00
 **/
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + ":start");
                lock.lock();
                try {
                    System.out.println(threadName + ":locking");
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();

                System.out.println(threadName + ":end");
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + ":start");
                try {
                    if(lock.tryLock(3, TimeUnit.SECONDS)){
                        try {
                            System.out.println(threadName + ":locking");
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        lock.unlock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println(threadName + ":end");
            }
        };




        new Thread(r).start();
        new Thread(r2).start();
        new Thread(r2).start();
        new Thread(r).start();


        Thread.sleep(100 * 1000);

    }

}
