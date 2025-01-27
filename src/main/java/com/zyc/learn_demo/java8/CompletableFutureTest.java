package com.zyc.learn_demo.java8;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description 测试异步
 * @Author zilu
 * @Date 2022/6/23 3:53 PM
 * @Version 1.0.0
 **/
public class CompletableFutureTest {

    @Test
    public void test2() {
        //创建一个已经有任务结果的CompletableFuture
        CompletableFuture<String> f1 = CompletableFuture.completedFuture("return value");
        //异步处理任务,有返回值
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::get);
        //异步处理任务,没有返回值
        CompletableFuture<Void> f3 = CompletableFuture.runAsync(System.out::println);
        //需要等待所有的异步任务都执行完毕,才会返回一个新的CompletableFuture
//        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
        //任意一个异步任务执行完毕,就会返回一个新的CompletableFuture
        CompletableFuture<Object> any = CompletableFuture.anyOf(f1, f2, f3);
        Object result = any.join();
        System.out.println("result = " + result);//result = return value
    }


    /**
     * 测试中断
     * 1. 任务结束前cancel，join 会抛出 CancellationException 异常
     * 2. 任务结束后cancel，正常执行
     */
    @Test
    public void testInterrupt() throws InterruptedException {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::sleep);
        Thread.sleep(15000);
//        Thread.sleep(1000);
        f2.cancel(false);
        System.out.println(f2.join());
    }

    @Test
    public void testInterruptWithApply() throws InterruptedException {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::sleep)
                .thenApply((r) -> {
                    System.out.println(r);
                    return r;
                } );
        Thread.sleep(1000);
        f2.cancel(false);
        System.out.println(f2.join());
    }


    @Test
    public void testInterruptWithApply2() throws InterruptedException {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::sleep2)
                .thenApply((r) -> {
                    System.out.println(r);
                    return r;
                } );
        System.out.println("hehe");
        // 使用thenApply会在join时才抛出异常
        System.out.println(f2.join());
    }

    /**
     *   handle 捕获不到cancel异常
     */
    @Test
    public void testInterruptWithHandler() throws InterruptedException {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::sleep)
                .handle((r,e) -> {
                    if(e != null) {
                        e.printStackTrace();
                    }
                    System.out.println(r);
                    return r;
                } );
        Thread.sleep(1000);
        f2.cancel(false);
        System.out.println(f2.join());
    }


    /**
     * 可以捕获运行时异常
     */
    @Test
    public void testInterruptWithHandler2() {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(this::sleep2)
                .handle((r,e) -> {
                    if(e != null) {
                        e.printStackTrace();
                    }
                    System.out.println(r);
                    return r;
                } );
        System.out.println(f2.join());
    }

    private String sleep2() {
        String a = 1/0 + "";
        return a + "_end";
    }

    private String sleep() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("11111");
        }
        return "sleep end";
    }

    public String get() {
        delay();
        return "异步任务结果";
    }

    public void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 自定义CompletableFuture 设置异常
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompleteExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
// ...
        completableFuture.completeExceptionally(
                new RuntimeException("Calculation failed!"));
// ...
        completableFuture.get(); // java.util.concurrent.ExecutionException: java.lang.RuntimeException: Calculation failed!
        System.out.println("end");
    }


    /**
     * 测试testWhenComplete由哪个线程执行？
     * 1. 注册whenComplete时，future还未完成，由调用complete的方法执行
     * 2. 注册whenComplete时，future已经完成，由注册whenComplete的线程执行
     */
    @Test
    public void  testWhenComplete() throws InterruptedException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        long start = System.currentTimeMillis();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            future.complete(true);
        }).start();
        Thread.sleep(6000);
        future.whenComplete((r,e) -> {
            if (r) {
                System.out.println(r);
            }
            if (e != null) {

            }
        });
        System.out.println(System.currentTimeMillis() - start);
        Thread.sleep(10000);
    }

}
