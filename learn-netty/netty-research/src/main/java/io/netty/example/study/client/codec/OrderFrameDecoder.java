package io.netty.example.study.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 一次解码
 * 粘包、半包的处理采用：固定长度字段存内容长度的方式
 * @author zhuyc
 * @date 2021/09/19 18:55
 **/
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {


    public OrderFrameDecoder() {
        /**
         * 参数分别表示
         * 1. 帐最大的长度。如果帧的长度大于这个值，将抛出 {@link TooLongFrameException}。
         * 2. 长度字段的偏移量
         * 3. 长度字段的长度
         * 4. 要添加到长度字段值的补偿值
         * 5. 从解码帧中剥离的第一个字节数（可以用来跳过前面的header部分字节）.2表示解码后的数据不要header了
         */
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
