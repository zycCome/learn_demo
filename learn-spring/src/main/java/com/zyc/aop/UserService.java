package com.zyc.aop;


import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


    /**
     * 事务超时测试
     *
     * 结论：Statement上设置的超时时间不是固定不变的。而是根据deadline的时间计算得到的
     * @throws InterruptedException
     */
    @Transactional(rollbackFor = IOException.class , timeout = 10)
    public void timeoutTest() throws InterruptedException {
        List<Map<String, Object>> maps = userDao.selectAll();

        Thread.sleep(5000);

        userDao.insert();

        Thread.sleep(5000);

    }

}

