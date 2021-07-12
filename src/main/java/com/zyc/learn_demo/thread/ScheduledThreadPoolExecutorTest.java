package com.zyc.learn_demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author zhuyc
 * @date 2021/06/23 07:44
 **/
public class ScheduledThreadPoolExecutorTest {


    @Test
    public void test1() throws InterruptedException {
        // 创建一个定时线程池
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() + ": 111111111");
            //测试抛出异常会不会中断定时任务。结论：会！之后不会将任务重新放入队列，因此也就不会再继续执行了！
            if (1 == 1) {
                throw new RuntimeException("222");
            }
//            //测试处理超时的影响.现象+源码可知：time早就延迟于当前时间了。因此worker每次从优先级队列中task,都可以马上拿到该任务
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }, 1, 2, TimeUnit.SECONDS);

        Thread.sleep(1231231);
        System.out.println("main");
    }

    /**
     * 测试取消调度
     */
    @Test
    public void test2() throws InterruptedException {
        // Create the scheduler
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        // Create the task to execute
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        // Schedule the task such that it will be executed every second
        ScheduledFuture<?> scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate(r, 1L, 1L, TimeUnit.SECONDS);
        // Wait 5 seconds
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Cancel the task
        scheduledFuture.cancel(false);
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello222");
            }
        };
        Thread.sleep(2000L);
        scheduledExecutorService.scheduleAtFixedRate(r2, 2L, 1L, TimeUnit.SECONDS);
        Thread.sleep(30000L);

    }


}
