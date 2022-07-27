package com.zyc.learn_demo.juccontainer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Description
 * @Author zilu
 * @Date 2022/7/13 11:54 AM
 * @Version 1.0.0
 **/
public class ListTest {


    @Test
    public void removeAllTest() {
        // 创建一个集合
        Collection c1 = new ArrayList();
        c1.add("abc1");
        c1.add("abc2");
        c1.add("abc3");
        c1.add("abc4");

        // 再创建一个集合
        Collection c2 = new ArrayList();
        c2.add("abc1");
        c2.add("abc2");
        c2.add("abc3");
        c2.add("abc4");
        c2.add("abc5");
        c2.add("abc6");
        c2.add("abc7");

        /*
         *注意：在removeAll的时候，c1.removeAll(c2)，c1删除的是c1和c2含有相同的元素，c2里的元素不变
         */
        // boolean removeAll(Collection c):从集合中删除一个指定的集合元素。
        // 只要有数据被删除，则返回true。
        System.out.println("removeAll:" + c1.removeAll(c2));

        System.out.println("c1:" + c1);
        System.out.println("c2:" + c2);
    }

    @Test
    public void retainAllTest() {
        // 创建一个集合
        Collection c1 = new ArrayList();
        c1.add("abc1");
        c1.add("abc2");
        c1.add("abc3");
        c1.add("abc4");
        c1.add("abc9");

        // 再创建一个集合
        Collection c2 = new ArrayList();
        c2.add("abc1");
        c2.add("abc2");
        c2.add("abc3");
        c2.add("abc4");
        c2.add("abc5");
        c2.add("abc6");
        c2.add("abc7");

        // boolean retainAll(Collection c)
        /*
         * 如果有两个集合A和B。
         * A对B做交集。
         * A集合保存的是交集元素。B集合不发生改变。
         * 返回值表示的是A集合是否发生过改变。
         */
        System.out.println("retainAll:" + c1.retainAll(c2));

        System.out.println("c1:" + c1);
        System.out.println("c2:" + c2);
    }

}
