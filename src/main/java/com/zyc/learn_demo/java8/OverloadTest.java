package com.zyc.learn_demo.java8;

import lombok.Data;
import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2024/1/5 11:25
 * @Version 1.0.0
 **/
public class OverloadTest {

    /**
     * 变量有声明类型的，按照最具体的类型找方法
     */
    @Test
    public void test1() {
        Integer integer1 = 1;
        Object o1 = integer1;
        Number o2 = integer1;

        String str= "aaa";
        OverloadObject overloadObject = new OverloadObject();
        overloadObject.say(o1);//i am say(Object):1
        overloadObject.say(o2);//i am say(Number):1
        overloadObject.say(integer1);//i am say(Integer):1
        overloadObject.say(str);//i am say(Object):aaa
    }


    /**
     * 没有声明类型的，按具体类型？其实直接创建的对象也是可以看作有声明类型的
     */
    @Test
    public void test2() {

        OverloadObject overloadObject = new OverloadObject();
        overloadObject.say(new Object());//i am say(Object):java.lang.Object@26f0a63f
        overloadObject.say(new String("i am string"));//i am say(Object):i am string
        overloadObject.say(2);//i am say(Integer):2
    }



    @Data
    public static class OverloadObject {

        public void say(Object o){
            System.out.println("i am say(Object):"+o);
        }

        public void say(Number o){
            System.out.println("i am say(Number):"+o);
        }


        public void say(Integer o){
            System.out.println("i am say(Integer):"+o);
        }
    }

}
