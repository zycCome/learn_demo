package com.zyc.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuyc
 * @date 2021/12/14 21:29
 **/
@Component
public class TxB {

    @Transactional(rollbackFor = Exception.class)
    public void b1() {
        System.out.println("b1");
    }

    public void b2() {
        System.out.println("b2");
    }

}
