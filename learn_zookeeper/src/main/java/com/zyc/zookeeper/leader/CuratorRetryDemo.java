package com.zyc.zookeeper.leader;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class CuratorRetryDemo {

    private static final String ZK_ADDRESS = "118.178.184.94:2181";
    private static final String NAMESPACE = "demo";
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        initClient(); // 1. 初始化客户端
        basicNodeOperations(); // 2. 基础节点操作
        retryTest();
        new CountDownLatch(1).await(); // 阻塞主线程保持监听
    }

    /** 1. 初始化客户端（含重试策略）[2,3](@ref) **/
    private static void initClient() {
        // 重试策略影响的是单次操作（如写数据、获取锁）的重试，和连接重试不是一回事情.连接重试会一直持续的。就好像数据库连接会一直重试，不可能网络断了几个小时，只重试前几个小时
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3); // 基础间隔1秒，最大重试3次
        client = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS)
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(2000)
                .retryPolicy(retryPolicy)
                .namespace(NAMESPACE) // 命名空间隔离
                .build();
        client.start();
        System.out.println("✅ ZK客户端已连接");
    }

    /** 2. 基础节点操作（CRUD）[1,5](@ref) **/
    private static void basicNodeOperations() throws Exception {
        String path = "/test-node";
        byte[] data = "Hello ZK".getBytes();

        // 递归创建持久节点（自动创建父路径）
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, data);
        System.out.println("📌 节点创建成功: " + path);

        // 读取节点数据和状态
        Stat stat = new Stat();
        byte[] result = client.getData().storingStatIn(stat).forPath(path);
        System.out.println("🔍 节点数据: " + new String(result) + " | 版本: " + stat.getVersion());

        // 更新数据（带版本校验）
        client.setData()
                .withVersion(stat.getVersion())
                .forPath(path, "Updated!".getBytes());

        // 删除节点（递归删除子节点）
        client.delete()
                .deletingChildrenIfNeeded()
                .forPath(path);
        log.info("🗑️ 节点已删除");
    }

    private static void retryTest() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);


        // 定义一个任务
        Runnable task = () -> {
            try {
                log.info("📌 开始创建节点");

                String path = "/test-retry";
                byte[] data = "Hello ZK".getBytes();

                // 递归创建持久节点（自动创建父路径）
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                        .forPath(path, data);
                log.info("📌 节点创建成功: " + path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

//        scheduledExecutorService.scheduleAtFixedRate(task, 0,30, TimeUnit.SECONDS);

        task.run();
        task.run();


    }
}
