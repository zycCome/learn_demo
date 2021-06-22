package com.zyc.learn_demo.concurrent;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhuyc
 * @date 2021/06/21 07:19
 **/
public class ThreadPoolTest {

    @Test
    public void testFutureCancel() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> submit = executorService.submit(() -> {
            System.out.println(111);
            return 222;
        });
        System.out.println(submit.get());
        submit.cancel(true);
        System.out.println(submit.get());
    }

    /**
     * Execute时检查检查工作线程数量时线程不安全的
     */
    @Test
    public void testExecuteSafe() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());
        new Thread(() -> {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 1");
            });
        }).start();
        new Thread(() -> {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 2");
            });
        }).start();


        System.out.println(111);

    }

    /**
     * execute中线程池关闭，task已经被执行，白加一个work的场景
     */
    @Test
    public void testExecuteMoreWork() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());
//        executor.allowCoreThreadTimeOut(true);
        new Thread(() -> {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 1");
            });
        }).start();
        new Thread(() -> {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 2");
            });
        }).start();
        new Thread(() -> {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 3");
            });
        }).start();
        //顺序如下：
        //第一个线程执行结束
        //第二个线程入队并执行结束,但还没有执行下面的检查
        //线程池关系
        //第二个线程执行下面的检查
        executor.shutdown();

        System.out.println(111);

    }


    /**
     * 需要确认两个问题
     * 1. 先park后interrupt是否不会阻塞
     * 2. 先interrupt后park是否不会阻塞
     */
    @Test
    public void testInterruptAndPark() {
        Thread thread = new Thread(() -> {
            System.out.println("1111");
            LockSupport.park();
            System.out.println("2222"+Thread.currentThread().isInterrupted());
            System.out.println("3333"+Thread.interrupted());
            System.out.println("4444"+Thread.currentThread().isInterrupted());

        });
        thread.start();

        thread.interrupt();

        System.out.println("main1");
        System.out.println("main2");

    }

}
