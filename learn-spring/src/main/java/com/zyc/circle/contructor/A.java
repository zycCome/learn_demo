package com.zyc.circle.contructor;

/**
 * @author zhuyc
 * @date 2022/04/18 11:38
 **/
public class A {
    private B b;

    public A (B b){
        this.b = b;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
