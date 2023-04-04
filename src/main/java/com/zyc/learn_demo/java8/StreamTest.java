package com.zyc.learn_demo.java8;

import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zilu
 * @Date 2022/12/5 7:50 PM
 * @Version 1.0.0
 **/
public class StreamTest {


    @Test
    public void test1() {
        Arrays.stream("a b c".split(" ")).map(e -> {
            System.out.println(e);
            return e+"_";
        }).forEach(System.out::println);
        System.out.println("end");
    }


    /**
     * 测试有状态的流操作
     */
    @Test
    public void test2() {
        Arrays.stream("a b c".split(" ")).sorted(Comparator.comparing(String::valueOf)).map(e -> {
            System.out.println(e);
            return e+"_";
        }).forEach(System.out::println);
        System.out.println("end");
    }


    /**
     * 测试有状态的流操作
     * 结论：会等所有的元素都处理后，才传入下一个流操作
     */
    @Test
    public void test3() {
        Arrays.stream("a b c".split(" ")).map(e -> {
            System.out.println(e);
            return e+"_";
        }).sorted(Comparator.comparing(String::valueOf)).forEach(System.out::println);
        System.out.println("end");
    }


    @Test
    public void test4() {
        Map a = new HashMap<>();
        a.put("name","a");
        Map b = new HashMap<>();
        b.put("name","b");
        a.put("child",b);
//        b.put("child",a);

        System.out.println(JSONUtil.toJsonStr(a));
    }

}
