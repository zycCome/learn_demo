package com.zyc.learn_demo.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2023/11/22 14:36
 * @Version 1.0.0
 **/
@Slf4j
public class SleepTest {


    /**
     * 测试sleep时打断
     */
    @Test
    public void testSleeping() {
        Thread t1 = new Thread(() -> {

            log.info("before");
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                log.info("InterruptedException");
            }

            log.info("after");
        });
        t1.start();
        t1.interrupt();

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 测试线程先interrupt，然后sleep.发现睡眠后，直接就是被打断了
     */
    @Test
    public void testBeforeSleepInterrupt() {
        Thread t1 = new Thread(() -> {

            log.info("before");
            Thread.currentThread().interrupt();
            log.info("interrupt1:{}",Thread.currentThread().isInterrupted());
            log.info("interrupt2:{}",Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                log.info("InterruptedException");
            }
            log.info("interrupt3:{}",Thread.currentThread().isInterrupted());
            log.info("after");
        });
        t1.start();

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
