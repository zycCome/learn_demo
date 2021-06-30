package com.zyc.learn_demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
            if (1 == 1)
                throw new RuntimeException("222");
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


}
