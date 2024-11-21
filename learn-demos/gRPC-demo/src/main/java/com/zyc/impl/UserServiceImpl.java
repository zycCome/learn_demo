package com.zyc.impl;



import com.zyc.protocol.User;
import com.zyc.protocol.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author Kone
 * @date 2022/1/29
 */
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void getUser(User request, StreamObserver<User> responseObserver) {
        System.out.println(request);
        User user = User.newBuilder()
                .setName("response name")
                .build();
        responseObserver.onNext(user);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsers(User request, StreamObserver<User> responseObserver) {
        System.out.println("get users");
        System.out.println(request);
        User user = User.newBuilder()
                .setName("user1")
                .build();
        User user2 = User.newBuilder()
                .setName("user2")
                .build();
        responseObserver.onNext(user);
        responseObserver.onNext(user2);

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<User> saveUsers(StreamObserver<User> responseObserver) {
        // 如果入参是Stream类型，那么只能通过返回一个StreamObserver来接受入参，通过onNext可以接受到多个对象
        return new StreamObserver<User>() {
            @Override
            public void onNext(User user) {
                System.out.println("get saveUsers list ---->");
                System.out.println(user);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("saveUsers error " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                User user = User.newBuilder()
                        .setName("saveUsers user1")
                        .build();
                responseObserver.onNext(user);
                responseObserver.onCompleted();
            }
        };
    }
}

