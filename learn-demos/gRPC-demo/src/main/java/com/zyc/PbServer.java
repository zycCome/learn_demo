package com.zyc;

import com.zyc.impl.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author zyc66
 * @date 2024/11/20 19:43
 **/
public class PbServer {

    public static void main(String[] args) throws Exception {
        int port = 9091;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new UserServiceImpl())
                .build()
                .start();
        System.out.println("server started, port : " + port);
        server.awaitTermination();
    }


}
