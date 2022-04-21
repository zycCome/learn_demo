package com.zyc.learn_demo.java8;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhuyc
 * @date 2022/03/27 17:11
 **/
public class OptionalTest {


    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.print(ClassLayout.parseClass(Dog.class).toPrintable());
        // 指针压缩了
        System.out.print(ClassLayout.parseClass(String.class).toPrintable());
        System.out.println(GraphLayout.parseInstance(dog).totalSize());
        //空字符40个字节。每个字符2个字节
        System.out.println(GraphLayout.parseInstance("").totalSize());
        System.out.println(GraphLayout.parseInstance("a").totalSize());
        System.out.println(GraphLayout.parseInstance("ab").totalSize());
        System.out.println(GraphLayout.parseInstance("abc").totalSize());
        System.out.println(GraphLayout.parseInstance("abcd").totalSize());
        System.out.println(GraphLayout.parseInstance("abcde").totalSize());
        System.out.println(GraphLayout.parseInstance("abcdef").totalSize());
        System.out.println(GraphLayout.parseInstance("abcdefg").totalSize());
        System.out.println(GraphLayout.parseInstance("abcdefgh").totalSize());
        System.out.println(GraphLayout.parseInstance("abcdefghi").totalSize());
        System.out.println(GraphLayout.parseInstance("abcdefghij").totalSize());
        System.out.println(GraphLayout.parseInstance("宁").totalSize());
        System.out.println(GraphLayout.parseInstance("宁好").totalSize());
        System.out.println(GraphLayout.parseInstance("宁我好").totalSize());
        // 中文和英文一样，都是两个字节
        System.out.println(GraphLayout.parseInstance("宁我好就").totalSize());

    }


    static class Dog {

        private int a = 1;
        private long c = 2L;
        private String d = "asd";

        public Dog() {
            System.out.println("dog init");
            int a = 1;
            List<String> l = new ArrayList<>();
//            l.forEach(e -> {
//                 a= 2;
//                 // lambda表达式使用外面的局部变量也必须是final修饰的
//
//            });
        }
    }

    /**
     * 测试获取值
     */
    @Test
    public void testGetValue() {
        Dog dog = new Dog();
        Optional<Dog> a = Optional.ofNullable(dog);
        System.out.println("----");

        a.orElseGet(Dog::new);

        // 这种方式有个弊端，无论是否有值，都会去创建对象
        System.out.println("----");
        a.orElse(new Dog());

    }

}
