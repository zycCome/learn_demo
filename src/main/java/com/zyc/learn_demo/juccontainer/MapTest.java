package com.zyc.learn_demo.juccontainer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

}
