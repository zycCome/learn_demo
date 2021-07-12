package com.zyc.learn_demo.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试compute
 *
 * @author zhuyc
 * @date 2021/7/8 16:31
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        ConcurrentHashMap<String,Long> map = new ConcurrentHashMap<>();
        Long a = map.compute("a",(k,v) -> {
            System.out.println(k);
            if(v == null) {
                v = 0L;
            }
            return ++v;

        });
        System.out.println("a:" + a);
        Long a1 = map.compute("a",(k,v) -> {
            System.out.println(k);
            if(v == null) {
                v = 0L;
            }
            return ++v;

        });
        System.out.println("a1:" + a1);
    }
}
