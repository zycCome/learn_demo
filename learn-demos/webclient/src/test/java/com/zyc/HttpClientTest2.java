package com.zyc;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.zyc.webclient.util.HttpTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description http客户端测试
 * @Author zilu
 * @Date 2022/7/4 8:41 PM
 * @Version 1.0.0
 **/
@Slf4j
public class HttpClientTest2 {

    int requestSize = 1000;

    AtomicInteger success = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);

    private CloseableHttpClient httpclient;


    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("okhttp-pool-").build();


    @Test
    public void testHttpClient() throws InterruptedException {

        String url = "https://ug.baidu.com/mcp/pc/pcsearch";
        String body = "{\"invoke_info\":{\"pos_1\":[{}],\"pos_2\":[{}],\"pos_3\":[{}]}}";

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100000), threadFactory);


        CountDownLatch countDownLatch = new CountDownLatch(100);
        long startTime = System.currentTimeMillis();

        requestWithHttpClient(url, body, 100, countDownLatch, threadPoolExecutor);
        countDownLatch.await();
        log.info("热身结束,耗时：" + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        countDownLatch = new CountDownLatch(requestSize);
        requestWithHttpClient(url, body, requestSize, countDownLatch, threadPoolExecutor);
        countDownLatch.await();
        log.info("耗时：{},success:{},failed:{}", (System.currentTimeMillis() - startTime), success.get(), failed.get());

    }


    private void requestWithHttpClient(String url, String body, int requestSize, CountDownLatch countDownLatch, ThreadPoolExecutor threadPoolExecutor) {
        for (int i = 0; i < requestSize; i++) {
            threadPoolExecutor.submit(() -> {
                        try {
                            String result = HttpTool.requestPost(url, body);
                            if(result == null) {
                                failed.incrementAndGet();
                            } else {
                                success.incrementAndGet();
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage(),e);
                        }
                        countDownLatch.countDown();

                    }
            );
        }
    }


}
