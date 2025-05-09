package com.zyc.learn_demo.juccontainer;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhuyc
 **/
public class SetTest {


    @Test
    public void removeIfTest() {
        // 初始化一个 HashSet， 有10个随机字符串
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(String.valueOf(i));
        }

        System.out.println(set);

        // 这种方式会报错
//        for (String s : set) {
//            if(Integer.parseInt(s) % 2== 0) {
//                set.remove(s);
//            }
//        }

        set.removeIf(s -> {
            if(Integer.parseInt(s) % 2== 0) {
                return true;
            }
            return false;
        });
        System.out.println(set);
    }


    /**
     * debug 看下Iterator源码
     */
    @Test
    public void iteratorTest() {
        // 初始化一个 HashSet， 有10个随机字符串
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            set.add(String.valueOf(i));
        }

        System.out.println(set);
        set.remove("2");

        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }
    }


}
