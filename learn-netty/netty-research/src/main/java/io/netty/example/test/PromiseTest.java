package io.netty.example.test;

import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zyc66
 * @date 2024/11/16 18:39
 **/
@Slf4j
public class PromiseTest {

    @Test
    public void test1() throws InterruptedException {
        DefaultEventLoop eventExecutors = new DefaultEventLoop();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventExecutors);
        // 设置回调，异步接收结果
        promise.addListener(future -> {
            // 这里的 future 就是上面的 promise
            log.debug("{}",future.getNow());
        });
        // 等待 1000 后设置成功结果
        eventExecutors.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("set success, {}",10);
            promise.setSuccess(10);
        });
        log.debug("start...");
        Thread.sleep(10000);
    }



    @Test
    public void tes2() throws InterruptedException {
        DefaultEventLoop eventExecutors = new DefaultEventLoop();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventExecutors);

        // 等待 1000 后设置成功结果
        eventExecutors.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("set success, {}",10);
            promise.setSuccess(10);
        });
        log.debug("start...");

        Thread.sleep(5000);
        // 设置回调，异步接收结果
        // 如果promise已经结束了，则listener直接被调用
        promise.addListener(future -> {
            // 这里的 future 就是上面的 promise
            log.debug("{}",future.getNow());
        });
        Thread.sleep(10000);
    }

}
