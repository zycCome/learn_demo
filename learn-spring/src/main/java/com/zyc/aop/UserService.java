package com.zyc.aop;


import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(rollbackFor = IOException.class)
    public void insertUser() {
        userDao.insert();
        System.out.println("插入完成...");
        UserService o = (UserService)AopContext.currentProxy();
        try {
            o.insertUserSuccess();
        } catch (Exception e) {
            System.out.println("内部异常");
        }
        int i = 10 / 0;
    }


//    @Transactional(rollbackFor = IOException.class ,propagation = Propagation.SUPPORTS)
    public void insertUserSuccess() {
        userDao.insert();
        System.out.println("插入完成...");
//        int i = 10 / 0;
    }

}

