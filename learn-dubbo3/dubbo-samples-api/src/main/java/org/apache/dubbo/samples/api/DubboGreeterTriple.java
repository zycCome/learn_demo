/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.apache.dubbo.samples.api;

import com.google.protobuf.Message;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.stub.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public final class DubboGreeterTriple {

    public static final String SERVICE_NAME = Greeter.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,Greeter.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME,GreeterOuterClass.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboGreeterTriple::newStub);
        StubSuppliers.addSupplier(Greeter.JAVA_SERVICE_NAME,  DubboGreeterTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(Greeter.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static Greeter newStub(Invoker<?> invoker) {
        return new GreeterStub((Invoker<Greeter>)invoker);
    }

    private static final StubMethodDescriptor greetMethod = new StubMethodDescriptor("greet",
    GreeterRequest.class, GreeterReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GreeterRequest::parseFrom,
    GreeterReply::parseFrom);

    private static final StubMethodDescriptor greetAsyncMethod = new StubMethodDescriptor("greet",
    GreeterRequest.class, CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GreeterRequest::parseFrom,
    GreeterReply::parseFrom);

    private static final StubMethodDescriptor greetProxyAsyncMethod = new StubMethodDescriptor("greetAsync",
    GreeterRequest.class, GreeterReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), GreeterRequest::parseFrom,
    GreeterReply::parseFrom);




    static{
        serviceDescriptor.addMethod(greetMethod);
        serviceDescriptor.addMethod(greetProxyAsyncMethod);
    }

    public static class GreeterStub implements Greeter{
        private final Invoker<Greeter> invoker;

        public GreeterStub(Invoker<Greeter> invoker) {
            this.invoker = invoker;
        }

        @Override
        public GreeterReply greet(GreeterRequest request){
            return StubInvocationUtil.unaryCall(invoker, greetMethod, request);
        }

        public CompletableFuture<GreeterReply> greetAsync(GreeterRequest request){
            return StubInvocationUtil.unaryCall(invoker, greetAsyncMethod, request);
        }

        public void greet(GreeterRequest request, StreamObserver<GreeterReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, greetMethod , request, responseObserver);
        }



    }

    public static abstract class GreeterImplBase implements Greeter, ServerService<Greeter> {

        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public CompletableFuture<GreeterReply> greetAsync(GreeterRequest request){
                return CompletableFuture.completedFuture(greet(request));
        }

        /**
        * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
        */
        public void greet(GreeterRequest request, StreamObserver<GreeterReply> responseObserver){
            greetAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<Greeter> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/greet" );
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/greetAsync" );

            BiConsumer<GreeterRequest, StreamObserver<GreeterReply>> greetFunc = this::greet;
            handlers.put(greetMethod.getMethodName(), new UnaryStubMethodHandler<>(greetFunc));
            BiConsumer<GreeterRequest, StreamObserver<GreeterReply>> greetAsyncFunc = syncToAsync(this::greet);
            handlers.put(greetProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(greetAsyncFunc));




            return new StubInvoker<>(this, url, Greeter.class, handlers);
        }


        @Override
        public GreeterReply greet(GreeterRequest request){
            throw unimplementedMethodException(greetMethod);
        }





        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
