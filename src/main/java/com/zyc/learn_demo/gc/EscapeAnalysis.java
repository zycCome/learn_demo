package com.zyc.learn_demo.gc;

import com.zyc.learn_demo.invoke_virtual.Person;

/**
 * @author zyc66
 * @date 2024/12/23 09:35
 **/
public class EscapeAnalysis {
    public Person p;
    /**
     * 发生逃逸，对象被返回到方法作用域以外，被方法外部，线程外部都可以访问
     */
    public void escape(){
        p = new Person(26, "TomCoding escape");
    }

    /**
     * 不会逃逸，对象在方法内部
     */
    public String noEscape(){
        Person person = new Person(266, "TomCoding noEscape");
        return person.name;
    }

    public Integer noEscapeInteger(){
        Person person = new Person(266, "TomCoding noEscape");
        return person.age;
    }

    static class Person {
        public Integer age;
        public String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

}



