package io.netty.example.study.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.study.server.codec.*;
import io.netty.example.study.server.codec.handler.MetricHandler;
import io.netty.example.study.server.codec.handler.OrderServerProcessHandler;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 服务端
 *
 * @author zhuyc
 * @date 2021/09/19 20:59
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //指定I/O 模式
        serverBootstrap.channel(NioServerSocketChannel.class);

        //添加netty自带的日志
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        MetricHandler metricHandler = new MetricHandler();

//        //非主从Reactor多线程模式
//        serverBootstrap.group(new NioEventLoopGroup(0, new DefaultThreadFactory("boss-and-worker")));
        serverBootstrap.group(new NioEventLoopGroup(2, new DefaultThreadFactory("boss")),
                new NioEventLoopGroup(0, new DefaultThreadFactory("worker")));
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                //下面handler的顺序是有规则的，如果粗了，可能不能正常工作了
                pipeline.addLast("orderFrameDecoder", new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());
//                pipeline.addLast("metricHandler", metricHandler);
                //添加日志输出
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                // 这个handler 不能放在后面，否则会接受不到read回调
                pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));

                pipeline.addLast(new OrderServerProcessHandler());


                //IdleStateHandler的readerIdleTime参数指定超过3秒还没收到客户端的连接，
                //会触发IdleStateEvent事件并且交给下一个handler处理，下一个handler必须
                //实现userEventTriggered方法处理对应事件
                pipeline.addLast(new HeartBeatServerHandler());


//                //因为FlushConsolidationHandler上没有@Sharable 注解，因此不能共享
//                pipeline.addLast("flushConsolidationHandler",new FlushConsolidationHandler(5,true));
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().get();

    }
}
