package com.zyc.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhuyc
 * @date 2022/04/21 11:31
 **/
public class ZkClientTest {


    private String path = "/watchDel";

    /**
     * 测试断联期间，监听的路径删除了。watch是否会生效
     *
     * 结果：可以监听到
     */
    @Test
    public void test() throws InterruptedException {
        ZkClient zkClient = new ZkClient(System.getenv("IP") + ":12181");

        zkClient.createEphemeral(path);

        // 创建监听
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                // 释放锁，删除节点时唤醒等待的线程
                System.out.println(s);
            }
        };

        // 注册监听
        zkClient.subscribeDataChanges("/watchDel", iZkDataListener);


//        // 节点存在时，等待节点删除唤醒
        if (zkClient.exists(path)) {
//            countDownLatch = new CountDownLatch(1);
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("path exist");
        } else {
            System.out.println("path not exist");

        }

        System.out.println("test");
        Thread.sleep(1000000);

//        // 删除监听
//        zkClient.unsubscribeDataChanges(PATH, iZkDataListener);

    }

}
