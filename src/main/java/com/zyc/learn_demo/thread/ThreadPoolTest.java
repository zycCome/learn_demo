package com.zyc.learn_demo.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhuyc
 * @date 2021/06/21 07:19
 **/
@Slf4j
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
     * Execute时检查工作线程数量是线程不安全的
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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
        new Thread(() -> {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 1");
                System.out.println(1/0);
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
//        try {
//            Thread.sleep(600 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //顺序如下：
        //第一个线程执行结束
        //第二个线程入队并执行结束,但还没有执行下面的检查
        //线程池关闭
        //第二个线程执行下面的检查
        executor.shutdown();

        System.out.println(111);

    }


    /**
     * 测试先插入队列还是先创建core-max之间的线程
     */
    @Test
    public void testMaxOrQueue() throws InterruptedException {
        ThreadPoolExecutor eventThreadPool = new ThreadPoolExecutor(1, 10, 30, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNamePrefix("workWx-event-thread-").build(), new CustomRejectedExecutionHandler());
        for (int i = 0; i < 12; i++) {
            int j = i;
            eventThreadPool.submit(() -> {

                log.info(Thread.currentThread().getName() + " start "+j);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info(Thread.currentThread().getName() + " end "+j);
            });
        }
        Thread.sleep(10000);
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

    /**
     *
     * isShutdown和isTerminated的区别？
     */
    @Test
    public void testIsShutdownAndIsTerminated() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());

        System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());
    }


    @Test
    public void testExecuteException() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory()
                , new ThreadPoolExecutor.AbortPolicy());
        executor.allowCoreThreadTimeOut(true);
        new Thread(() -> {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " --- 1");
                System.out.println(1/0);
                System.out.println("end");
            });
        }).start();

        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //顺序如下：
        //第一个线程执行结束
        //第二个线程入队并执行结束,但还没有执行下面的检查
        //线程池关闭
        //第二个线程执行下面的检查
        executor.shutdown();

        System.out.println(111);

    }

    @Test
    public void testCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            System.out.println(111);
        });
        executorService.submit(() -> {
            System.out.println(111);
        });

        System.out.println("end");
    }


    @Test
    public void testSynchronousQueue() throws InterruptedException {
        // 该场景,队列中不会有一个任务在等待(因为这个队列存不了东西)
        ThreadPoolExecutor distributeExecutor = new ThreadPoolExecutor(0, 3, 30, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNamePrefix("pushServer-thread-").build(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 4; i++) {
            int j = i;
            //第四个线程直接报错了
            distributeExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " i:"+ j + "start");
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " i:"+ j + "end");

            });
        }

        Thread.sleep(20 * 1000);

    }


    /**
     * 测试SynchronousQueue能否阻塞多个线程.结论可以：并且默认是用不公平的栈
     * @throws InterruptedException
     */
    @Test
    public void testSynchronousQueue2() throws InterruptedException {
        // 该场景,队列中不会有一个任务在等待(因为这个队列存不了东西)
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        for (int i = 0; i < 4; i++) {
            int j = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronousQueue.put("push object:" + j);
                        System.out.println("thread1:"+j + " consumer:"+j);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            thread.start();
        }

        System.out.println("push complete");

        for (int i = 0; i < 4; i++) {
            int j = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        String take = synchronousQueue.take();
                        System.out.println("thread:"+j + " consumer:"+take);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            thread.start();
        }

        Thread.sleep(20 * 1000);

    }



}
