package com.zyc.learn_demo.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @Description
 * @Author zilu
 * @Date 2024/5/7 16:56
 * @Version 1.0.0
 **/
public class MulticastReceiver {

    public static void main(String[] args) throws Exception {
        InetAddress group = InetAddress.getByName("239.100.0.1");
        int port = 5555;
        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(group);//加入到组播组
            byte[] buffer = new byte[8192];
            while (true) {
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);//接收组播数据报
                String s = new String(dp.getData(), 0, dp.getLength());
                System.out.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally
        {
            if (ms !=null)
            {
                try
                {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException e) { }
            }
        }
    }


}
