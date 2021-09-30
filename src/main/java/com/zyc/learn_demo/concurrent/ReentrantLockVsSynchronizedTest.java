package com.zyc.learn_demo.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Synchronized和Lock
 *
 * @author zhuyc
 * @date 2021/9/30 17:21
 */
public class ReentrantLockVsSynchronizedTest {

    public static AtomicInteger a = new AtomicInteger(0);
    public static LongAdder b = new LongAdder();
    public static int c = 0;
    public static int d = 0;
    public static int e = 0;

    public static final ReentrantLock fairLock = new ReentrantLock(true);
    public static final ReentrantLock unfairLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------------------------------");
        testAll(1, 100000);
        System.out.println("-------------------------------------");
        testAll(2, 100000);
        System.out.println("-------------------------------------");
        testAll(4, 100000);
        System.out.println("-------------------------------------");
        testAll(6, 100000);
        System.out.println("-------------------------------------");
        testAll(8, 100000);
        System.out.println("-------------------------------------");
        testAll(10, 100000);
        System.out.println("-------------------------------------");
        testAll(50, 100000);
        System.out.println("-------------------------------------");
        testAll(100, 100000);
        System.out.println("-------------------------------------");
        testAll(200, 100000);
        System.out.println("-------------------------------------");
        testAll(500, 100000);
        System.out.println("-------------------------------------");
        testAll(500, 10000);
        System.out.println("-------------------------------------");
        testAll(500, 1000);
        System.out.println("-------------------------------------");
        testAll(500, 100);
        System.out.println("-------------------------------------");
        testAll(500, 10);
        System.out.println("-------------------------------------");
        testAll(500, 1);
        System.out.println("-------------------------------------");
    }


    /**
     * @param threadCount 线程数量
     * @param loopCount   循环次数
     */
    private static void testAll(int threadCount, int loopCount) throws InterruptedException {
        testAtomicInteger(threadCount, loopCount);
        testLongAddr(threadCount, loopCount);
        testSynchronized(threadCount, loopCount);
        testReentrantLockUnfair(threadCount, loopCount);
        testReentrantLockFair(threadCount, loopCount);

    }

    private static void testReentrantLockFair(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < loopCount; i1++) {
                    fairLock.lock();
                    try {
                        c++;
                    } finally {
                        fairLock.unlock();
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testReentrantLockFair: result=" + c + ", threadCount=" + threadCount + ",loopCount=" + loopCount + ",elapse=" + (System.currentTimeMillis() - start));

    }

    private static void testReentrantLockUnfair(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < loopCount; i1++) {
                    unfairLock.lock();
                    try {
                        d++;
                    } finally {
                        unfairLock.unlock();
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testReentrantLockUnfair: result=" + d + ", threadCount=" + threadCount + ",loopCount=" + loopCount + ",elapse=" + (System.currentTimeMillis() - start));

    }

    private static void testSynchronized(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < loopCount; i1++) {
                    synchronized (ReentrantLockVsSynchronizedTest.class) {
                        e++;
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testSynchronized: result=" + e + ", threadCount=" + threadCount + ",loopCount=" + loopCount + ",elapse=" + (System.currentTimeMillis() - start));
    }

    private static void testLongAddr(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < loopCount; i1++) {
                    b.increment();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testLongAddr: result=" + b.sum() + ", threadCount=" + threadCount + ",loopCount=" + loopCount + ",elapse=" + (System.currentTimeMillis() - start));
    }

    private static void testAtomicInteger(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < loopCount; i1++) {
                    a.incrementAndGet();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("testAtomicInteger: result=" + a.get() + ", threadCount=" + threadCount + ",loopCount=" + loopCount + ",elapse=" + (System.currentTimeMillis() - start));
    }


}
