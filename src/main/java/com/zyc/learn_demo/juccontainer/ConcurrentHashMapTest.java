package com.zyc.learn_demo.juccontainer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试并发安全的map
 *
 * @author zhuyc
 * @date 2021/06/27 10:00
 **/
public class ConcurrentHashMapTest {

    @Test
    public void test1() {
        /*
        默认大小16
         */
        ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
        map1.put("A", "a");
        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>(15);
        map2.put("A", "a");
        ConcurrentHashMap<String, String> map3 = new ConcurrentHashMap<>(16);
        map3.put("A", "a");
    }


    /**
     * 测试并发场景下，扩容时 源数组中bin的第一个元素是否会变化
     * 对应代码 putVal-synchronized代码块里的第二次检查：再次检测第一个元素是否有变化
     *
     * 结论：发生变化了，bin的第一个元素变成null了
     */
    @Test
    public void testTransferAffect() {
        ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
        map1.put("A", "a");
        map1.put("A", "c");
        map1.put("B", "a");
        map1.put("C", "a");
        map1.put("D", "a");
        map1.put("E", "a");
        map1.put("F", "a");
        map1.put("G", "a");
        map1.put("H", "a");
        map1.put("1", "a");
        new Thread(() -> {
            map1.put("Hi", "a");
        }).start();
        map1.put("2", "a");
        map1.put("3", "a");
        map1.put("4", "a");
        map1.put("5", "a");
        map1.put("6", "a");
        map1.put("7", "a");
    }

    @Test
    public void testMap() {
        HashMap<String, String> map1 = new HashMap<>(8);
        map1.put("A", "a");
        map1.put("A", "c");
        map1.put("B", "a");
        map1.put("C", "a");
        map1.put("D", "a");
        map1.put("E", "a");
        map1.put("F", "a");
        map1.put("G", "a");
        map1.put("H", "a");
        map1.put("1", "a");
        new Thread(() -> {
            map1.put("Hi", "a");
        }).start();
        map1.put("2", "a");
        map1.put("3", "a");
        map1.put("4", "a");
        map1.put("5", "a");
        map1.put("6", "a");
        map1.put("7", "a");
    }


    /**
     * 测试帮助扩容失败怎么办？不帮了，不阻塞，直接往下走。反正已经拿到了最新的table（虽然可能还在迁移中），但不影响put的正常使用
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> m1 = new ConcurrentHashMap(16);
        new Thread(() -> {
            m1.put("key25","a");
            System.out.println(111);
        }).start();
        for (int i = 0; i < 32; i++) {
            m1.put("key"+i,"xc");
        }

    }

}
