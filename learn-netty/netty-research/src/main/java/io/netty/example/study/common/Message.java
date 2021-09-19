package io.netty.example.study.common;

import io.netty.buffer.ByteBuf;
import io.netty.example.study.util.JsonUtil;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * 消息类
 * @param <T> 继承MessageBody，限定了messageBody属性的类型
 */
@Data
public abstract class Message<T extends MessageBody> {

    /**
     * 有两个属性。header和body
     */
    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    /**
     * 将Message编码成ByteBuf
     * @param byteBuf
     */
    public void encode(ByteBuf byteBuf) {
        //注意这里的顺序，和上面图中不太一样。这里的streamId才是第二个
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        //将body序列化成json 字符串，然后获取字节数组。最后写入byteBuf
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    /**
     * 定义一个抽象类，根据opcode 返回具体解码的body的类型
     * @param opcode 操作码
     * @return 具体解码的body的类型
     */
    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    /**
     * 将ByteBuf 解码成一个具体的消息
     * @param msg
     */
    public void decode(ByteBuf msg) {
        // 按encode一样的顺序，取出header里面的3个属性
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        // 赋值header
        this.messageHeader = messageHeader;

        //获取具体的body类型
        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        /*
        * 1. 先将将 msg此缓冲区的可读字节解码为具有指定字符集名称的字符串。（可读字节不包括前面已经读取到的部分）
        * 2. 通过JsonUtil反序列化成具体的Body对象
         */
        T body = JsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")), bodyClazz);
        this.messageBody = body;
    }

}
