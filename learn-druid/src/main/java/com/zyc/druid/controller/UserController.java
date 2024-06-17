package com.zyc.druid.controller;

import com.zyc.druid.pojo.po.User;
import com.zyc.druid.repository.UserRepos;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuyc
 * @date 2021/10/10 11:44
 **/

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "saveUser")
    public User saveUser() {
        System.out.println(dataSource);
        return userRepos.save(new User());
    }

    @RequestMapping(value = "/findByUserName")
    public List<User> findByUserName(String userName) {
        return userRepos.findByUserName(userName);
    }

    @RequestMapping(value = "findByUserNameLike")
    public List<User> findByUserNameLkie(String userName) {
        return userRepos.findByUserNameLike(userName);
    }

    @RequestMapping(value = "findByPage")
    public Page<User> findByPage(Integer userType) {
        return userRepos.findByUserType(userType, PageRequest.of(1, 5));
    }



    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("test/lock")
    public String testLock() {
        RLock lock = redissonClient.getLock("zycLock");
        try {
            //加锁10s，不设置加密狗，不搞死锁。
            // redission的订阅机制是先订阅，然后再竞争一次锁，最大wait时间是现有锁ttl时间（但ttl可能会续期）
            boolean b = lock.tryLock(300, TimeUnit.SECONDS);
            if (b) {
                //todo 这里写业务逻辑
                return "123";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("分布式锁异常了，" + e.getMessage());
        } finally {
            lock.unlock();
        }
        return null;
    }


}
