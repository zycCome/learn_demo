package com.zyc.learn_demo.java8;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @date 2022/05/05 17:07
 **/
public class TestHello {

    public static String hello = "hello";

    static {
//        System.out.println("TestHello");
    }

    int a = 2;

    public TestHello() {
        // 结论：实例字段的赋值会整合到每个init方法中
        System.out.println(a);
        System.out.println(b);
        a=4;
        b = 6;
        System.out.println(a);
        System.out.println(b);
    }

    public TestHello(int c) {
        // 所有的构造函数字节码层面都会生成init方法
        a=41;
        b = 61;
        System.out.println(a);
        System.out.println(b);
    }

    int b = 3;


    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        int c = a + 1;
    }
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<String,Integer>(){};
        System.out.println(map);
        System.out.println("111");
        int a = 12;
        int b = 13;
        String s = "111";
        new TestHello();
        long l = 12L;

        int c = a+b;
        Object o = new Object();
        s = "222";

        s = s+l;

        l = Long.parseLong("222");
        System.out.println(l);



    }

}
