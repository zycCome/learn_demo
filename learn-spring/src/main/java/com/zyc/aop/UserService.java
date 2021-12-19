package com.zyc.aop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        int i = 10 / 0;
    }


    public void insertUserSuccess() {
        userDao.insert();
        System.out.println("插入完成...");
    }

}

