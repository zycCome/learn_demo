package com.zyc.transaction;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ITxA {

    public void  a();

}
