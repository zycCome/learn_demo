package io.netty.example.study.server.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.study.common.Operation;
import io.netty.example.study.common.OperationResult;
import io.netty.example.study.common.RequestMessage;
import io.netty.example.study.common.ResponseMessage;

/**
 * server端处理请求消息
 *
 * @author zhuyc
 * @date 2021/09/19 19:25
 **/
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation messageBody = requestMessage.getMessageBody();
        OperationResult operationResult = messageBody.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        // 这里header不用变。因为header就是version、opCode、streamId
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        // 响应客户端
        ctx.writeAndFlush(responseMessage);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 测试编解码对channelReadComplete的影响。测试方式：客户端发送两个消息，查看有调用了几次channelReadComplete。结果：调用了一次！
        System.out.println("OrderServerProcessHandler channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }
}
