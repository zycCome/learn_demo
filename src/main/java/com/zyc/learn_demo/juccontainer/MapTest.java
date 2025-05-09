package com.zyc.learn_demo.juccontainer;

import org.junit.Test;

import java.util.*;

/**
 * @author zhuyc
 * @date 2022/03/08 21:23
 **/
public class MapTest {

    @Test
    public void get() {
        HashMap<String,String> map = new HashMap(2);

        map.put("a","a");
        map.put("b","b");
        map.put("b1","b");
        map.put("b2","b");
        map.put("b3","b");
        map.keySet();
        map.values();
        System.out.println(map.get("a"));
        System.out.println(map.get("d"));

    }


    @Test
    public void getLinkedHashMap() {
        LinkedHashMap<String,String> map = new LinkedHashMap(2);

        map.put("a","a");
        map.remove("a");

        System.out.println(map.get("a"));
        System.out.println(map.get("d"));

    }


    public void testGeneric() {
        List<String> a = new ArrayList<>();
        a.add("asdas");


        String s = a.get(0);
    }


    @Test
    public void removeIfTest() {
        // 初始化一个 HashMap， 有10个随机字符串
        HashMap<Integer,Integer> map = new HashMap();
        for (int i = 0; i < 10; i++) {
            map.put(i,i*10);
        }

        System.out.println(map);

        // 这种方式会报错
//        for (String s : map) {
//            if(Integer.parseInt(s) % 2== 0) {
//                map.remove(s);
//            }
//        }
        map.keySet().removeIf(s -> {
            if(s % 2== 0) {
                return true;
            }
            return false;
        });
        System.out.println(map);
    }

}
