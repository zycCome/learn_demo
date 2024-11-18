package io.netty.example.study.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.study.client.codec.OrderFrameDecoder;
import io.netty.example.study.client.codec.OrderFrameEncoder;
import io.netty.example.study.client.codec.OrderProtocolDecoder;
import io.netty.example.study.client.codec.OrderProtocolEncoder;
import io.netty.example.study.common.RequestMessage;
import io.netty.example.study.common.order.OrderOperation;
import io.netty.example.study.util.IdUtil;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

/**
 * 客户端
 *
 * @author zhuyc
 * @date 2021/09/19 20:59
 **/
public class Client {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        //指定I/O 模式
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //下面handler的顺序是有规则的，如果粗了，可能不能正常工作了。
                //注意这里类的packgae需要是客户端的。如果搞错了，就不能正常收发消息，但奇怪的是netty也不报错！！！
                pipeline.addLast(new FixedLengthFrameDecoder(20));
                pipeline.addLast(new FixedLengthFrameDecoder(20));
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());

                //添加日志输出
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
        //必须等待连接成功之后，再发送消息
        channelFuture.sync();
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"));
        channelFuture.channel().writeAndFlush(requestMessage);
        channelFuture.channel().writeAndFlush(requestMessage);

        channelFuture.channel().closeFuture().get();
        System.out.println("已经关闭");

    }
}
