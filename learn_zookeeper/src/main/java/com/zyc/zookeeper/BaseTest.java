package com.zyc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基本用法
 *
 * @author zhuyc
 * @date 2022/04/21 08:43
 **/
public class BaseTest {

    final static Logger logger = LoggerFactory.getLogger(BaseTest.class);


    CuratorFramework client;

    @Before
    public void initial() {
        String host = System.getenv("IP");
        logger.info("[初始化]");
        // 工厂创建，fluent风格
        client = CuratorFrameworkFactory.builder()
                // ip端口号
                .connectString(host + ":12181")
                // 会话超时
                .sessionTimeoutMs(5000)
                // 重试机制，这里是超时后1000毫秒重试一次
                .retryPolicy(new RetryNTimes(0, 1000))
                // 名称空间，在操作节点的时候，会以这个为父节点
                .namespace("curator")
                .build();

        client.start();

        // 返回STARTED并不是一定连上了
        logger.info("初始化-连接状态:{}", client.getState());
    }

    @After
    public void destroy() {
        logger.info("[销毁]");
        client.close();
    }

    // ids权限
    @Test
    public void create1() throws Exception {
        try {
            // 新增节点
            client.create()
                    // 节点的类型
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    // 节点的acl权限列表
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    // arg1：节点路径，arg2：节点数据
                    .forPath("/node1", new byte[0]);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("create1 success");
    }


    @Test
    public void watcher1() throws Exception {
        client.create()
                // 节点的类型
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                // 节点的acl权限列表
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                // arg1：节点路径，arg2：节点数据
                .forPath("/node1", new byte[0]);
        logger.info("create1 success");


        // arg1 curator的客户端
        // arg2 监视的路径
        NodeCache nodeCache = new NodeCache(client, "/watcher1");

        // 启动
        nodeCache.start();

        // 测试发现：不知道是不是本地缓存的原因，即使session失效重连后，watcher依旧能监听到断联期间的节点删除事件
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            // 节点变化时的回调方法
            public void nodeChanged() throws Exception {

                // 节点删除时，getCurrentData()返回null
                // 路径
                System.out.println(nodeCache.getCurrentData().getPath() + "  " + nodeCache.getCurrentData().getStat());
                // 输出节点内容
                System.out.println(new String(nodeCache.getCurrentData().getData()));
            }


        });

        // null就是不存在
        Stat stat = client.checkExists().forPath("/watcher1");


        System.out.println("注册完成");
        // 时间窗内可以一直监听
        //        TimeUnit.SECONDS.sleep(1000);
        //关 闭
        nodeCache.close();
    }

}
