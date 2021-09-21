package io.netty.example.study.common;

/**
 * 请求消息
 *
 * 这里的Message的泛型是声明为Operation（RequestBody的子类）
 * ，因此所有RequestMessage产生的RequestBody都可以执行Operation的execute方法！
 */
public class RequestMessage extends Message<Operation>{

    /**
     * 请求也是需要先解码出具体body的
      * @param opcode 操作码
     * @return
     */
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

    public RequestMessage(){}

    public RequestMessage(Long streamId, Operation operation){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }

}
