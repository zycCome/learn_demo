package com.zyc.zookeeper.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class LeaderElectionWithCurator {

    private static final String ZK_ADDRESS = "118.178.184.94:2181";
    private static final String ELECTION_PATH = "/service/leader";
    private final String nodeId;
    private CuratorFramework client;
    private volatile LeaderLatch leaderLatch;
    private volatile long listenerCount = 0;

    public LeaderElectionWithCurator(String nodeId) throws Exception {
        this.nodeId = nodeId;
        initZkClient();
        registerConnectionListener();
        startElection();
    }

    private void initZkClient() {
        // 重试策略：初始间隔1s，最多重试3次
        // 重试策略影响的是单次操作（如写数据、获取锁）的重试，和连接重试不是一回事情.连接重试会一直持续的。就好像数据库连接会一直重试，不可能网络断了几个小时，只重试前几个小时
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 1);
        client = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS)
                .sessionTimeoutMs(60000) // 会话超时时间（ZK自动清理节点）
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    public void registerConnectionListener() {
        // 添加连接状态监听（处理网络闪断）
        client.getConnectionStateListenable().addListener((client, newState) -> {
            if (newState == ConnectionState.LOST) {
                System.out.println(nodeId + ": 网络连接丢失，释放Leader身份-"+ Thread.currentThread().getName() + " client-"+client);
            } else if (newState == ConnectionState.RECONNECTED) {
                System.out.println(nodeId + ": 网络重连成功，尝试重新选举-"+ Thread.currentThread().getName() + " client-"+client);
                // TODO 尝试不手动重新选举，leaderLatch是否会自动重新选举。结论不用重新创建，本来就会自动重新选举
//                try {
//                    leaderLatch.close(); // 关闭旧实例
//                    startElection();     // 重新参与选举
//                } catch (IOException e) {
//                    System.out.println("关闭异常1");
//                    throw new RuntimeException(e);
//                } catch (Exception e) {
//                    System.out.println("关闭异常2");
//                    throw new RuntimeException(e);
//                }
            }
        });
    }

    private void startElection() throws Exception {
        leaderLatch = new LeaderLatch(client, ELECTION_PATH, nodeId);

        // 添加选举状态监听
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                onElectedAsLeader(); // 成为Leader
            }
            @Override
            public void notLeader() {
                //
                // 客户端主动检测：ZK客户端（如Curator）会在会话超时达到 2/3时（即 20000 * 2/3 ≈ 13333ms）主动触发超时逻辑，提前标记会话失效

                /**
                 * 网络异常时：
                 * sessionTimeoutMs【Min(客户端配置sessionTimeoutMs值，服务端maxSessionTimeout)】的2/3时就会触发 State change: SUSPENDED 状态，然后触发该方法，不会等到完全超时才执行该方法。
                 * 异常处理： LeaderLatch实例可以增加ConnectionStateListener来监听网络连接问题。 当 SUSPENDED 或 LOST 时, leader不再认为自己还是leader。
                 */
                System.out.println(nodeId + ": 降级为Follower");
            }
        });



        leaderLatch.start(); // 开始选举
    }

    private void onElectedAsLeader() {
        System.out.println(">>> " + nodeId + " 成为Leader，开始对外服务 <<<");
        // 模拟服务运行（实际替换为业务逻辑）
        new Thread(() -> {
            while (leaderLatch.hasLeadership()) {
                try {
                    System.out.println(nodeId + ": 正在提供服务...");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        String nodeId = args.length > 0 ? args[0] : "node-default";
        LeaderElectionWithCurator election = new LeaderElectionWithCurator(nodeId);

        // 阻塞主线程
        System.in.read();
        CloseableUtils.closeQuietly(election.leaderLatch);
        CloseableUtils.closeQuietly(election.client);
    }
}
