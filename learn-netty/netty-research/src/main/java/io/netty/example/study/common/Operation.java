package io.netty.example.study.common;


/**
 * 这里继承了MessageBody就是为了添加一个抽象方法！！！关键
 */
public abstract class Operation extends MessageBody{

    /**
     * 具体响应的处理逻辑
     * @return
     */
    public abstract OperationResult execute();

}
