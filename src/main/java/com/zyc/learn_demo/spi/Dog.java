package com.zyc.learn_demo.spi;

/**
 * @author zhuyc
 * @date 2021/06/02 07:02
 **/
public class Dog implements IAnimal {

    @Override
    public void say() {
        System.out.println("狗吠========");
    }
}
