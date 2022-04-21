package com.zyc.zookeeper.lock;

/**
 * @author zhuyc
 * @date 2022/04/21 10:45
 **/
public abstract class AbstractLock implements Lock {

    /**
     * 获取锁
     */
    @Override
    public void getLock() {

        if (tryLock()) {
            System.out.println("--------获取到了自定义Lock锁的资源--------");
        } else {
            // 没拿到锁则阻塞，等待拿锁
            waitLock();
            getLock();
        }

    }

    /**
     * 尝试获取锁，如果拿到了锁返回true，没有拿到则返回false
     */
    public abstract boolean tryLock();

    /**
     * 阻塞，等待获取锁
     */
    public abstract void waitLock();
}

