package io.netty.example.study.common;

/**
 * 响应消息
 */
public class ResponseMessage extends Message <OperationResult>{


    /**
     * 实现了返回要解码的body的类型
     * @param opcode 操作码
     * @return
     */
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationResultClazz();
    }
}
