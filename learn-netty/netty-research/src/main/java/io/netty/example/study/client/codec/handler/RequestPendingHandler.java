package io.netty.example.study.client.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.study.client.dispatcher.RequestPendingCenter;
import io.netty.example.study.common.ResponseMessage;

/**
 * @author zhuyc
 * @date 2021/09/19 22:27
 **/
public class RequestPendingHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    private RequestPendingCenter requestPendingCenter;

    public RequestPendingHandler(RequestPendingCenter requestPendingCenter) {
        this.requestPendingCenter = requestPendingCenter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage responseMessage) throws Exception {
        requestPendingCenter.set(responseMessage.getMessageHeader().getStreamId(),
                responseMessage.getMessageBody());
    }
}
