package com.zyc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2023/6/9 2:18 PM
 * @Version 1.0.0
 **/
public class LocalTest {

    private final static  String CLUSTER_CONNECT_STR="192.168.4.91:2181,192.168.4.92:2181,192.168.4.93:2181";



    @Test
    public void testCluster() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CLUSTER_CONNECT_STR)
                .sessionTimeoutMs(1000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new RetryNTimes(0, 1000))
                .build();
        client.start();

        String path = "/";
        byte[] buf = client.getData().forPath(path);
        System.out.println("get data path:"+path+", data:"+new String(buf));


    }


}
