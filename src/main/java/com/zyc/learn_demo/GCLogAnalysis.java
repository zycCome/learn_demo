package com.zyc.learn_demo;

import cn.hutool.core.util.RandomUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author zyc66
 * @date 2024/12/15 23:32
 **/
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
//        int size = 1024 * 1024;
        int size = 1024 * 10;

        Map<Integer,byte[]> cache = new HashMap<>();
        for (int i = 0; i < 10000; ++i) {
            byte[] alloc1 = new byte[size * 10];
//            System.out.println("alloc1");
            cache.put(i,alloc1);
//            cache.remove(i-7);
            if(i % 100 == 0) {
                System.out.println("alloc1");
            }
            if(i % 500 == 0) {
//                System.gc();
            }
            if(RandomUtil.randomInt(100)  < 20) {
                cache.remove(i-100);
            }

//            byte[] alloc2 = new byte[size * 2];
//            System.out.println("alloc2");
//
//            byte[] alloc3 = new byte[size * 4];
//            System.out.println("alloc3");
        }
    }


}
