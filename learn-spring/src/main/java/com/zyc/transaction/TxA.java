package com.zyc.transaction;

import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @date 2021/12/14 21:27
 **/
@Component
public class TxA implements ITxA{


    @Override
    public void a() {
        System.out.println("a");
    }
}
