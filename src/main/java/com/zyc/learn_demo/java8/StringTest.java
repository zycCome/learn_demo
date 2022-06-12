package com.zyc.learn_demo.java8;

import java.lang.reflect.Field;

/**
 * @author zhuyc
 * @date 2022/05/05 16:28
 **/
public class StringTest {

    public static void main(String[] args) throws Exception {
        String str1 = "hello";
        String str2 = new String("hello");
        String str3 = "world";
        String str4 = "hello";

        System.out.println(str1 == str2); // false
        System.out.println(str1 == str3); // false
        System.out.println(str1 == str4); // true

        // 通过反射，更改底层 char 数组
        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        char[] chars = "world".toCharArray();
        field.set(str1, chars);

        System.out.println(str1); // world
        System.out.println(str2); // hello
        System.out.println(str3); // world
        System.out.println(str4); // world

        System.out.println("---");
        System.out.println("hello"); // world
        System.out.println("hello" == str1); // world
//        System.out.println(str2.intern());
        System.out.println(TestHello.hello == str1);
        System.out.println(TestHello.hello);
        System.out.println("---");


        System.out.println(str1 == str2); // false
        System.out.println(str1 == str3); // false
        System.out.println(str1 == str4); // true
    }


}
