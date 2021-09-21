package io.netty.example.study.client.dispatcher;

import io.netty.example.study.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuyc
 * @date 2021/09/19 22:23
 **/
public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture future = this.map.get(streamId);
        if (future != null) {
            future.setSuccess(operationResult);
            this.map.remove(streamId);
        }
    }

}
