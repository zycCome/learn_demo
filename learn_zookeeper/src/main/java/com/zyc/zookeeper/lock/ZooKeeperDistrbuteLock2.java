package com.zyc.zookeeper.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhuyc
 * @date 2022/04/21 10:56
 **/
public class ZooKeeperDistrbuteLock2 extends ZooKeeperAbstractLock {

    private CountDownLatch countDownLatch = null;
    /**
     * 当前请求节点的前一个节点
     */
    private String beforePath;
    /**
     * 当前请求的节点
     */
    private String currentPath;

    public ZooKeeperDistrbuteLock2() {
        if (!zkClient.exists(PATH)) {
            // 创建持久节点，保存临时顺序节点
            zkClient.createPersistent(PATH);
        }
    }

    @Override
    public boolean tryLock() {
        // 如果currentPath为空则为第一次尝试拿锁，第一次拿锁赋值currentPath
        if (currentPath == null || currentPath.length() == 0) {
            // 在指定的持久节点下创建临时顺序节点
            currentPath = zkClient.createEphemeralSequential(PATH + "/", "lock");
        }
        // 获取所有临时节点并排序，例如：000044
        List<String> childrenList = zkClient.getChildren(PATH);
        Collections.sort(childrenList);

        if (currentPath.equals(PATH + "/" + childrenList.get(0))) {
            // 如果当前节点在所有节点中排名第一则获取锁成功
            return true;
        } else {
            // TODO：也有可能自己的节点也没有了
            int wz = Collections.binarySearch(childrenList, currentPath.substring(6));
            beforePath = PATH + "/" + childrenList.get(wz - 1);
        }
        return false;
    }

    @Override
    public void waitLock() {
        // 创建监听
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                // 释放锁，删除节点时唤醒等待的线程
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };

        // 注册监听，这里是给排在当前节点前面的节点增加（删除数据的）监听，本质是启动另外一个线程去监听前置节点
        zkClient.subscribeDataChanges(beforePath, iZkDataListener);

        // 前置节点存在时，等待前置节点删除唤醒
        if (zkClient.exists(beforePath)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 删除对前置节点的监听
        zkClient.unsubscribeDataChanges(beforePath, iZkDataListener);
    }

    /**
     * 释放锁
     */
    @Override
    public void unLock() {
        if (zkClient != null) {
            System.out.println("释放锁资源");
            zkClient.delete(currentPath);
            zkClient.close();
        }
    }


}
