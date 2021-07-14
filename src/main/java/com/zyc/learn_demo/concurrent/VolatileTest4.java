package com.zyc.learn_demo.concurrent;

/**
 * volatile的内存屏障
 *
 * @author zhuyc
 * @date 2021/7/14 10:16
 */
public class VolatileTest4 {
    // a不使用volatile修饰
    public static long a = 0;
    // 消除缓存行的影响
    public static long p1, p2, p3, p4, p5, p6, p7;
    // b使用volatile修饰
    public static volatile long b = 0;
    // 消除缓存行的影响
    public static long q1, q2, q3, q4, q5, q6, q7,q8;
    // c不使用volatile修饰
    public static long c = 0;


    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (a == 0) {
                long x = b;
            }
            System.out.println("a=" + a);
        }).start();


        new Thread(()->{
            while (c == 0) {
                long x = b;
            }
            System.out.println("c=" + c);
        }).start();


        Thread.sleep(100);


        a = 1;
        b = 1;
        Thread.sleep(1000);
        if(b == 1) {
            c = 1;
        }

        Thread.sleep(60 * 1000);
    }
}
