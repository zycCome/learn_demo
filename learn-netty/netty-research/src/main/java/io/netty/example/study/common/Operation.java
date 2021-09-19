package io.netty.example.study.common;

public abstract class Operation extends MessageBody{

    /**
     * 具体响应的处理逻辑
     * @return
     */
    public abstract OperationResult execute();

}
