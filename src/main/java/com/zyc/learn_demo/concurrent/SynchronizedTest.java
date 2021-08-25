package com.zyc.learn_demo.concurrent;

import java.io.IOException;
import java.net.*;

/**
 * Synchronized公平锁测试
 *
 * @author zhuyc
 * @date 2021/07/15 07:36
 **/
public class SynchronizedTest {

    public static void sync(String tips) {
        synchronized (SynchronizedTest.class) {
            System.out.println(tips);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        /**
         * 结论：不是公平锁
         */
        new Thread(()->sync("线程1")).start();
        Thread.sleep(100);
        new Thread(()->sync("线程2")).start();
        Thread.sleep(100);
        new Thread(()->sync("线程3")).start();
        Thread.sleep(100);
        new Thread(()->sync("线程4")).start();
    }



    public void testBindSpecificIp() throws IOException {
//        InetAddress addr = Inet4Address.getByAddress(new byte[]{127,0,0,1});
//        ServerSocket serverSocket = new ServerSocket(8080,50, addr);
        ServerSocket serverSocket = new ServerSocket();
//        serverSocket.bind(new InetSocketAddress(8888));
        serverSocket.bind(new InetSocketAddress("192.168.1.100",8888));
    }


}
