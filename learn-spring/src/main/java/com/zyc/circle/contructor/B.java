package com.zyc.circle.contructor;

/**
 * @author zhuyc
 * @date 2022/04/18 11:38
 **/
public class B {
    private C c;

    public B (C c){
        this.c = c;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }
}
