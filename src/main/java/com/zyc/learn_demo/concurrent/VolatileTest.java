package com.zyc.learn_demo.concurrent;

import org.junit.jupiter.api.Test;

/**
 * volatile测试
 *
 * @author zhuyc
 * @date 2021/7/14 9:38
 */
public class VolatileTest {

    @Test
    public void visibilityTest() throws InterruptedException {
        Thread thread = new Thread(() -> VisibilityTestClass.checkFinished());
        thread.start();
        Thread.sleep(100);
        VisibilityTestClass.finish();
        System.out.println("main finish1");
        thread.join();
        System.out.println("main finish2");

    }

    static class VisibilityTestClass {
//        public static volatile int finished = 0;
        public static int finished = 0;

        private static void checkFinished() {
            while (finished == 0) {

            }
            System.out.println("finished");
        }

        public static void finish() {
            finished = 1;
        }
    }

}
