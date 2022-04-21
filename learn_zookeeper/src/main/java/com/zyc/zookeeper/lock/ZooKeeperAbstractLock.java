package com.zyc.zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author zhuyc
 * @date 2022/04/21 10:47
 **/
public abstract class ZooKeeperAbstractLock extends AbstractLock {

    private static String SERVER_ADDR;

    {
        SERVER_ADDR = System.getenv("IP") + ":12181";
    }


    protected ZkClient zkClient = new ZkClient(SERVER_ADDR);

    protected static final String PATH = "/lock";


}
