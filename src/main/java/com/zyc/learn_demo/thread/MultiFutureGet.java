package com.zyc.learn_demo.thread;

import java.util.concurrent.*;

/**
 * 多次调用失败的Future的get方法，结果：多次失败
 *
 * @author zhuyc
 * @date 2021/06/18 23:51
 **/
public class MultiFutureGet {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int a = 100;
        a -= 50;
        System.out.println(a);
        Future<?> future = executor.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1111);
            if (2 > 1)
                throw new RuntimeException("haha");
            System.out.println(2222);
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        System.out.println(111);
    }
}
