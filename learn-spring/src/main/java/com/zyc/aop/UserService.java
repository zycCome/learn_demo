package com.zyc.aop;


import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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



    @Transactional(rollbackFor = Exception.class)
    public void insertWithTransactionSynchronization() {
        boolean isSynchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();

        if (isSynchronizationActive) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    //代码走到这里时，事务已经完成了（可能是回滚了、或者是提交了）
                    System.out.println("afterCompletion");
                }
            });
        } else {
            //无事务的，直接插入并投递消息

            System.out.println("无事务");
        }
        userDao.insert();
        System.out.println("插入完成...");

//        int i = 10 / 0;

        UserService o = (UserService)AopContext.currentProxy();
        o.nestedInsert();
    }



    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)

    public void nestedInsert() {
        boolean isSynchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();

        if (isSynchronizationActive) {
            // 如果传播级别是REQUIRED，则在嵌套调用的场景下，会在一个事务中添加两个Synchronization
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {

                // 如果传播级别是REQUIRED，则在嵌套调用的场景下,内部方法提交，不会触发该方法。因为只有在最外层事务提交时触发
                @Override
                public void afterCompletion(int status) {
                    //代码走到这里时，事务已经完成了（可能是回滚了、或者是提交了）
                    System.out.println("nestedInsert afterCompletion");
                }
            });
        } else {
            //无事务的，直接插入并投递消息

            System.out.println("nestedInsert 无事务");
        }
        userDao.insert();
        System.out.println("nestedInsert 插入完成...");
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

