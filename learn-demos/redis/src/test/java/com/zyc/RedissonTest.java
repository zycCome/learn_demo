package com.zyc;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author zyc66
 * @date 2024/12/15 09:58
 **/
public class RedissonTest {

    private RedissonClient redissonClient;

    @Before
    public void initial() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.23.148.54:6379")
                .setPassword("123456").setUsername("default");
        redissonClient =  Redisson.create(config);
    }



    @Test
    public void read_lock_watch_dog_case() {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("zycLock");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        RLock rLock = readWriteLock.readLock();
        try {

            rLock.lock();
            System.out.println("----get lock----");
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            rLock.unlock();
            System.out.println("----unlock lock----");
        }
    }

}
