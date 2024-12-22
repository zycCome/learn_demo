package com.zyc.learn_demo.invoke_virtual;

/**
 * @author zyc66
 * @date 2024/12/19 19:02
 **/
public class Zyc extends Person implements Fly{


    @Override
    public String getName() {
        return "Zyc";
    }


    public static void main(String[] args) {
        //invokespecial #4 <com/zyc/learn_demo/invoke_virtual/Zyc.<init> : ()V>
        Zyc zyc = new Zyc();
        // invokevirtual #6 <com/zyc/learn_demo/invoke_virtual/Zyc.getName : ()Ljava/lang/String;>
        System.out.println(zyc.getName());
        // invokevirtual #8 <com/zyc/learn_demo/invoke_virtual/Zyc.fly : ()Ljava/lang/String;>
        System.out.println(zyc.fly());

        Person person = zyc;
        // invokevirtual #9 <com/zyc/learn_demo/invoke_virtual/Person.getName : ()Ljava/lang/String;>
        System.out.println(person.getName());

        Fly fly = zyc;
        // invokeinterface #10 <com/zyc/learn_demo/invoke_virtual/Fly.fly : ()Ljava/lang/String;>
        System.out.println(fly.fly());
    }

    @Override
    public String fly() {
        try {
            return "i can fly";

        } finally {
            System.out.println("finaly");
        }
    }
}
