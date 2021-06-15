package com.zyc.learn_demo.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuyc
 * @date 2021/06/05 10:31
 **/
public class TestJStackInfo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(() -> {
            System.out.println("run 1");
            try {
                reentrantLock.lock();

                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
            System.out.println("run 2");
        }).start();

        Thread.sleep(1000);
        try {
            reentrantLock.lock();
        } finally {
            reentrantLock.unlock();;
        }

        System.out.println("end");

    }

    private volatile  int a = 1;

    private void testWrite() {
        a++ ;
        int b = a;
        System.out.println(b);
    }

    private void testRead() {
        int b = a;
        int c = 2;
        System.out.println(b + c);
    }

}
