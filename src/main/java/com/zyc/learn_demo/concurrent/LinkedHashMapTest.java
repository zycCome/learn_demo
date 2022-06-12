package com.zyc.learn_demo.concurrent;

import java.util.LinkedHashMap;

/**
 * @author zhuyc
 * @date 2022/05/02 08:53
 **/
public class LinkedHashMapTest {

    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap =
                new LinkedHashMap<String, String>();
        linkedHashMap.put("111", "111");
        linkedHashMap.put("222", "222");
    }

//    接口中的变量都是常量
//    interface A {
//
//        public int a = 0;
//
//        {
//            a = 2;
//        }
//
//    }

}
