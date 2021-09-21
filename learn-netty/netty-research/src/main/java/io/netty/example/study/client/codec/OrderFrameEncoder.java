package io.netty.example.study.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 一次编码
 * 粘包、半包的处理采用：固定长度字段存内容长度的方式
 *
 * @author zhuyc
 * @date 2021/09/19 18:55
 **/
public class OrderFrameEncoder extends LengthFieldPrepender {


    public OrderFrameEncoder() {
        //和一次编码对应，长度字节固定2
        super(2);
    }
}
