package com.zyc.learn_demo.spi;

/**
 * @author zhuyc
 * @date 2021/06/02 07:01
 **/
public class Cat implements IAnimal {

    @Override
    public void say() {
        System.out.println(this.toString()+"猫叫========");
    }
}