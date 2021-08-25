package com.zyc.learn_demo.concurrent;

import org.junit.jupiter.api.Test;

/**
 * 伪共享测试
 *
 * @author zhuyc
 * @date 2021/07/14 07:41
 **/
public class FalseSharingTest {

    @Test
    public void test() throws InterruptedException {
        Point point = new Point();
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                point.x++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                point.y++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(point);

    }



    static class Point {
        volatile long x;
//        long p1,p2,p3,p4,p5,p6,p7;
        volatile long y;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
