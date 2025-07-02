package com.zyc.zookeeper.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

public class CuratorStateDemo {

    private static long originalSessionId = 0;


    public static void main(String[] args) throws Exception {
        // 1. 初始化 Curator 客户端（短会话超时：10秒）
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("118.178.184.94:2181")
                .sessionTimeoutMs(10000) // 10秒超时，便于测试 LOST 状态
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        // 2. 添加连接状态监听器
        client.getConnectionStateListenable().addListener((curatorClient, newState) -> {
            switch (newState) {
                case CONNECTED:
                    try {
                        originalSessionId = client.getZookeeperClient().getZooKeeper().getSessionId();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("[状态] CONNECTED: 已连接ZooKeeper");
                    break;
                case SUSPENDED:
                    System.out.println("[状态] SUSPENDED: 连接临时中断（会话未失效）");
                    break;
                case RECONNECTED:
                    System.out.println("[状态] RECONNECTED: ");
                    long currentSessionId = 0;
                    try {
                        currentSessionId = client.getZookeeperClient().getZooKeeper().getSessionId();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (currentSessionId != originalSessionId) {
                        System.out.println("会话已变更！需重建临时节点和监听器");
//                        rebuildEphemeralNodes(); // 重建临时节点
//                        restartCaches();        // 重启监听器
                    } else {
                        System.out.println("原会话恢复，无需额外操作");
                    }
                    originalSessionId = currentSessionId;
                    break;
                case LOST:
                    System.out.println("[状态] LOST: 会话超时失效！临时节点将被删除");
                    break;
                case READ_ONLY:
                    System.out.println("[状态] READ_ONLY: 只读模式（连接Observer节点）");
                    break;
            }
        });

        client.start();
        System.out.println("客户端已启动，等待连接...");

        // 3. 创建临时节点（会话失效时会被ZK自动删除）
        String ephemeralPath = "/test-ephemeral";
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(ephemeralPath, "初始数据".getBytes());
        System.out.println("创建临时节点: " + ephemeralPath);

        // 4. 模拟不同场景（取消注释以测试）
        // testShortDisconnect(client); // 测试短暂中断 -> SUSPENDED -> RECONNECTED
        // testLongDisconnect(client);  // 测试长中断 -> LOST -> 重建会话
        System.in.read();
    }

    // 重建临时节点（用于 RECONNECTED 状态）
    private static void recreateEphemeralNode(CuratorFramework client) {
        try {
            String path = "/test-ephemeral";
            if (client.checkExists().forPath(path) == null) {
                client.create()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(path, "重建数据".getBytes());
                System.out.println("临时节点已重建");
            }
        } catch (Exception e) {
            System.err.println("重建临时节点失败: " + e.getMessage());
        }
    }

    // 场景1：模拟短暂网络中断（触发 SUSPENDED -> RECONNECTED）
    private static void testShortDisconnect(CuratorFramework client) throws Exception {
        System.out.println("\n--- 测试：模拟短暂网络中断（5秒）---");
        Thread.sleep(5000);
        System.out.println("手动断开网络...（等待5秒）");
        // 此处应通过工具（如 iptables）断开网络，或重启ZK服务
        Thread.sleep(5000); // 等待状态变更
        System.out.println("恢复网络...");
        // 状态将自动恢复为 RECONNECTED
    }

    // 场景2：模拟长时中断（触发 LOST）
    private static void testLongDisconnect(CuratorFramework client) throws Exception {
        System.out.println("\n--- 测试：模拟长时中断（15秒 > 会话超时）---");
        Thread.sleep(5000);
        System.out.println("手动断开网络...（等待15秒）");
        Thread.sleep(15000); // 超过 sessionTimeoutMs(10秒)
        System.out.println("恢复网络...");
        // 状态将变为 LOST，需重建客户端
        CloseableUtils.closeQuietly(client);
        System.out.println("客户端已关闭，请重启程序测试重建会话");
    }
}
