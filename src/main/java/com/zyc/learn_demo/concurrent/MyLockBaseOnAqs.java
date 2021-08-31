package com.zyc.learn_demo.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.stream.IntStream;

/**
 * 基于AQS的自定义锁
 *
 * @author zhuyc
 * @date 2021/08/26 22:04
 **/
public class MyLockBaseOnAqs {

    //私有内部类，自定义同步
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private Sync sync = new Sync();

    private void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static int count;

    public static void main(String[] args) throws InterruptedException {
        MyLockBaseOnAqs lock = new MyLockBaseOnAqs();
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        IntStream.range(0,1000).forEach(i -> new Thread(() -> {
            lock.lock();
            try {
                IntStream.range(0,10000).forEach(j -> {
                    count++;
                });
            } finally {
                lock.unlock();
            }
            countDownLatch.countDown();
        },"tt-"+i).start());
        countDownLatch.await();
        System.out.println(count);

    }


}
