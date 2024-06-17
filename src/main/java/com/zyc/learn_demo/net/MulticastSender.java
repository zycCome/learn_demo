package com.zyc.learn_demo.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @Description
 * @Author zilu
 * @Date 2024/5/7 17:01
 * @Version 1.0.0
 **/
public class MulticastSender {

    public static void main(String[] args) throws IOException {
        InetAddress group = InetAddress.getByName("239.100.0.1");
        int port = 5555;
        MulticastSocket ms = null;

        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(group);//加入到组播组
            while (true) {
                String message = "Hello " + new java.util.Date();
                byte[] buffer = message.getBytes();
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);
                ms.send(dp);//发送组播数据报
                System.out.println("发送数据报给" + group + ":" + port);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ms != null) {
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException e) {
                }

            }
        }
    }


}
