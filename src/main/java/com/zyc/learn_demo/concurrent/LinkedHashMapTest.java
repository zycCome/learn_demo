package com.zyc.learn_demo.concurrent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @date 2022/05/02 08:53
 **/
public class LinkedHashMapTest {

    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap =
                new LinkedHashMap<String, String>(4);
        linkedHashMap.put("111", "111");
        linkedHashMap.put("222", "222");
        linkedHashMap.put("3", "3");
        linkedHashMap.put("4", "4");
        linkedHashMap.put("5", "5");
        for (Map.Entry<String, String> stringStringEntry : linkedHashMap.entrySet()) {
            System.out.println(stringStringEntry.getKey()+":"+stringStringEntry.getValue());
        }


        System.out.println("----------------");

        LRUCache<String, String> lruCache =
                new LRUCache<String, String>(4);
        lruCache.put("111", "111");
        lruCache.put("222", "222");
        lruCache.put("3", "3");
        lruCache.put("4", "4");
        lruCache.put("5", "5");
        for (Map.Entry<String, String> stringStringEntry : lruCache.entrySet()) {
            System.out.println(stringStringEntry.getKey()+":"+stringStringEntry.getValue());
        }
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
