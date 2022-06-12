package com.zyc.zookeeper.state;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhuyc
 * @date 2022/05/07 10:22
 **/

public class ZkMonitorService {

//    @Resource
    private CuratorFramework zkClient;


    final static Logger log = LoggerFactory.getLogger(ZkMonitorService.class);


    private static String NODE = "/mainDataNode";

//    @PostConstruct
    public void monitorRegister() {
        String ip = NetUtil.localIpv4s().stream().findFirst().get();
        try {
            //注册主节点
            Stat stat = zkClient.checkExists().forPath(NODE);
            if (ObjectUtil.isEmpty(stat)) {
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(NODE, NODE.getBytes("UTF-8"));
            }
            //注册临时节点
            String subNode = NODE + "/" + ip + "_";
            //验证
            Stat stat1 = zkClient.checkExists().forPath(subNode);
            if (Objects.isNull(stat1)) {
                zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(subNode, ip.getBytes("UTF-8"));
                log.info("=======服务节点启动,注册临时节点：{}", subNode);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s创建子节点失败，请重新尝试", ip));
        }
    }

//    /**
//     * 创建监听器实例,监听状态变更事件
//     */
//    @Override
//    public void run(String... args) throws Exception {
//        //注册监听
//        OrderMonitorListener stateListener = new OrderMonitorListener(zkClient);
//        zkClient.getConnectionStateListenable().addListener(stateListener);
//    }


}

