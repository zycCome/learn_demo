package com.zyc.learn_demo.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhuyc
 * @date 2022/03/18 07:21
 **/
public class ReentrantReadWriteLockTest {


    @Test
    public void test() {
        test2(false);
    }


    /**
     * 测试目的：
     * 队列中有不连续的读线程阻塞时，是否会唤醒全部的读线程。如排队是 R R W R
     * 结论： 无论公平锁还非公平锁，只要排队了，就是按队列中的顺序依次唤醒！
     *
     * @param fair 是否是公平锁
     */
    public void test2(boolean fair) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(fair);
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        // W1
        new Thread() {
            @Override
            public void run() {
                writeLock.lock();
                System.out.println("W1 start");
                System.out.println("W1 end");
                writeLock.unlock();
            }
        }.start();

        // R1
        new Thread() {
            @Override
            public void run() {
                readLock.lock();
                System.out.println("R1 start");
                System.out.println("R1 end");
                readLock.unlock();
            }
        }.start();


        // R2
        new Thread() {
            @Override
            public void run() {
                readLock.lock();
                System.out.println("R2 start");
                System.out.println("R2 end");
                readLock.unlock();
            }
        }.start();


        // W2
        new Thread() {
            @Override
            public void run() {
                writeLock.lock();
                System.out.println("W2 start");
                System.out.println("W2 end");
                writeLock.unlock();
            }
        }.start();


        // R3
        new Thread() {
            @Override
            public void run() {
                readLock.lock();
                System.out.println("R3 start");
                System.out.println("R3 end");
                readLock.unlock();
            }
        }.start();


        System.out.println("end 1");
        System.out.println("end 2");
    }

}
