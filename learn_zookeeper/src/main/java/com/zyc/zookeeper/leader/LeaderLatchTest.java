package com.zyc.zookeeper.leader;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.List;

/**
 * @author zhuyc
 * @date 2025/07/02
 */
public class LeaderLatchTest {
    // zk地址
    private static final String CONNECT_STR = "118.178.184.94:2181";
    // leader的path
    private static final String LEADER_PATH = "/app/leader";

    public static void main(String[] args) {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderLatch> leaderLatches = Lists.newArrayList();

        try {
            // 模拟10个节点
            for(int i=0; i<10; i++) {
                // 创建客户端
                CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_STR, new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                // 创建leaderLatch
                LeaderLatch leaderLatch = new LeaderLatch(client, LEADER_PATH, "Client #" + i, LeaderLatch.CloseMode.NOTIFY_LEADER);
                // 设置选主成功后触发的监听器
                leaderLatch.addListener(new LeaderLatchListener() {
                    @Override
                    public void isLeader() {
                        System.out.println("我是leader");
                    }

                    @Override
                    public void notLeader() {
                        System.out.println("我不是leader");
                    }
                });
                leaderLatches.add(leaderLatch);
                // 启动客户端
                client.start();
                // 启动leaderLatch
                leaderLatch.start();
            }
            // 等待一段时间，让选举leader成功
            Thread.sleep(10000);

            // 当前的leader节点
            LeaderLatch currentLeaderLatch = null;
            // 遍历leaderLatch,找出主节点
            for (LeaderLatch leaderLatch : leaderLatches) {
                // 判断当前是不是主节点
                if (leaderLatch.hasLeadership()) {
                    currentLeaderLatch = leaderLatch;
                }
            }
            System.out.println("当前Leader是 " + currentLeaderLatch.getId());

            // 关闭Leader节点
            System.out.println("关闭Leader " + currentLeaderLatch.getId());
            currentLeaderLatch.close();
            // 从列表中移除
            leaderLatches.remove(currentLeaderLatch);

            // 等待一段时间，重新选择Leader
            System.out.println("重新选择Leader中.....");
            Thread.sleep(5000);
            for (LeaderLatch leaderLatch : leaderLatches) {
                // 判断当前是不是主节点
                if (leaderLatch.hasLeadership()) {
                    currentLeaderLatch = leaderLatch;
                }
            }
            System.out.println("重现选出的Leader是 " + currentLeaderLatch.getId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (LeaderLatch leaderLatch : leaderLatches) {
                CloseableUtils.closeQuietly(leaderLatch);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }

    }
}

