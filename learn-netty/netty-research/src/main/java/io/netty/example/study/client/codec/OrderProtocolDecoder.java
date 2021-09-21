package io.netty.example.study.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.example.study.common.RequestMessage;
import io.netty.example.study.common.ResponseMessage;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 二次解码
 * 将ByteBuf转成ResponseMessage对象(这里用ResponseMessage,因为这里是客户端接收到了响应)
 *
 * @author zhuyc
 * @date 2021/09/19 19:16
 **/
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(msg);

        //这一步很重要，如果不写。则上面的转化就是无效的
        out.add(responseMessage);
    }
}
