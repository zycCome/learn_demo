package com.zyc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zyc.protocol.FileOuterClass;
import com.zyc.protocol.User;
import com.zyc.protocol.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

/**
 * @author zyc66
 * @date 2024/11/20 19:44
 **/
public class PbTest {


    public static void main(String[] args) throws InterruptedException {
        FileOuterClass.File file = FileOuterClass.File.newBuilder()
                .setName("fileName")
                .setSize(200)
                .build();
        User user = User.newBuilder()
                .setUsername("zhangsan")
                .setUserId(100)
                .putHobbys("pingpong", "play pingpong")
                .setCode(200)
                .setFile(file)
//                .setError("error string")
                .build();

        System.out.println(user);
        System.out.println(user.getMsgCase().getNumber());


//        测试反序列化
        try {
            FileOuterClass.File fileNew = FileOuterClass.File.parseFrom(file.toByteArray());
            System.out.println(fileNew);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        String host = "127.0.0.1";
        int port = 9091;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

//        client接收一个对象
        UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        User responseUser = userServiceBlockingStub.getUser(user);
        System.out.println(responseUser);

//        client接收一个列表
        Iterator<User> users = userServiceBlockingStub.getUsers(user);
        while (users.hasNext()) {
            System.out.println(users.next());
        }

//         client发送一个列表到Server端
        UserServiceGrpc.UserServiceStub userServiceStub = UserServiceGrpc.newStub(channel);

        StreamObserver<User> responseObserver = new StreamObserver<User>() {
            @Override
            public void onNext(User user) {
                System.out.println("responseObserver -------> ");
                System.out.println(user);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("responseObserver onError " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("responseObserver onCompleted");
            }
        };
        StreamObserver<User> requestObserver = userServiceStub.saveUsers(responseObserver);

        for (int i = 0; i < 3; i++) {
            requestObserver.onNext(user);
        }

        requestObserver.onCompleted();

        Thread.sleep(2000);
        channel.shutdown();
    }


}
