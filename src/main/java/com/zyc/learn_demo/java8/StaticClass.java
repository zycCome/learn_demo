package com.zyc.learn_demo.java8;

/**
 * @Description
 * @Author zilu
 * @Date 2024/5/27 20:16
 * @Version 1.0.0
 **/
public class StaticClass {

    static int a;
    final static int c = 99;
    static {
        a = 1;
        b = 1;
    }
    static int b =100;
    public static void main(String[] args) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }


    public int test(int ar) {
        int c = 1;
        System.out.println(c);
        int b = 2;
        int d = 3;

        double d1 = 0.0;
//        double d2 = 0.0;
        System.out.println(b);
        if(ar > 1) {
            int f = 1;
        } else {
            int g = 2;
        }
        return c+b;
    }
}
