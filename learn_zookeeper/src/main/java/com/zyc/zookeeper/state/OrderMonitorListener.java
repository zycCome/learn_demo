package com.zyc.zookeeper.state;

import cn.hutool.core.net.NetUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuyc
 * @date 2022/05/07 10:20
 **/
public class OrderMonitorListener implements ConnectionStateListener {


    final static Logger log = LoggerFactory.getLogger(OrderMonitorListener.class);


    private CuratorFramework zkClient;

    public OrderMonitorListener(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.LOST) {
            log.info("===========监听到zk节点发生LOST事件");
        } else if (connectionState == ConnectionState.CONNECTED) {
            log.info("===========监听到zk节点发生CONNECTED事件");
        } else if (connectionState == ConnectionState.RECONNECTED) {
            log.info("===========监听到zk节点发生RECONNECTED事件:【RECONNECTED不会重新创建临时节点,需要手动创建】");
//            String ip = NetUtil.localIpv4s().stream().findFirst().get();
//            String node = "/mainDataNode/" + ip + "_";
//            try {
//                zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(node, ip.getBytes("UTF-8"));
//                log.info("=======服务节点RECONNECTED重连,重新创建临时节点：{}", node);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }
    }

//    @Override
//    public boolean doNotDecorate() {
//        return false;
//    }

}

