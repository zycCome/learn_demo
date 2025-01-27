package com.zyc;

import java.io.File;
import java.io.IOException;

import org.apache.curator.test.TestingServer;

/**
 * Embed ZooKeeper.
 *
 * <p>
 * Only used for examples
 * </p>
 */
public final class EmbedZookeeperServer {

    private static TestingServer testingServer;

    /**
     * Embed ZooKeeper.
     *
     * @param port ZooKeeper port
     */
    public static void start(final int port) {
        try {
            testingServer = new TestingServer(port, new File(String.format("target/test_zk_data/%s/", System.nanoTime())));
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    testingServer.close();
                } catch (final InterruptedException | IOException ignore) {
                }
            }));
        }
    }
}

