package com.zyc;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description http客户端测试
 * @Author zilu
 * @Date 2022/7/4 8:41 PM
 * @Version 1.0.0
 **/
@Slf4j
public class HttpClientTest3 {

    int requestSize = 1000;

    AtomicInteger success = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);

    private CloseableHttpClient httpclient;


    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("okhttp-pool-").build();


    @Test
    public void testHttpClient() throws InterruptedException {
        initial();
        String url = "https://ug.baidu.com/mcp/pc/pcsearch";
//        String url = "http://localhost:8080/date";
        String body = "{}";

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000,
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
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                return EntityUtils.toString(response.getEntity());
            }
        };




        for (int i = 0; i < requestSize; i++) {
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            threadPoolExecutor.submit(() -> {
                        try {
                            HttpPost httpPost = new HttpPost(url);
                            httpPost.setEntity(new StringEntity(body, Consts.UTF_8));
                            String result = httpClient.execute(httpPost, responseHandler); // 线程可能会在这里被阻塞
                            if (result == null) {
                                failed.incrementAndGet();
                            } else {
                                success.incrementAndGet();
                            }
                        } catch (Exception e) {
                            failed.incrementAndGet();
                            log.error(e.getMessage(), e);
                        }
                        countDownLatch.countDown();
                    }
            );
        }
    }


    private void initial() {
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return -1 ;//如果没有约定，则默认定义时长为0s
            }
        };

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10000);
        connectionManager.setDefaultMaxPerRoute(200);//例如默认每路由最高50并发，具体依据业务来定

        HttpParams params = new BasicHttpParams();
        //设置连接超时时间
        Integer CONNECTION_TIMEOUT = 20 * 1000; //设置请求超时2秒钟 根据业务调整
        Integer SO_TIMEOUT = 20 * 1000; //设置等待数据超时时间2秒钟 根据业务调整

        //定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
        //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置。
        Long CONN_MANAGER_TIMEOUT = 50000L; //在httpclient4.2.3中我记得它被改成了一个对象导致直接用long会报错，后来又改回来了

        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        //在提交请求之前 测试连接是否可用
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(myStrategy)
                .setDefaultRequestConfig(RequestConfig.custom().setStaleConnectionCheckEnabled(true).build())
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .evictExpiredConnections()
                // 开启后台线程清除闲置的连接
                .evictIdleConnections(30, TimeUnit.SECONDS)
//                .setConnectionTimeToLive(40000)
                .build();

//        httpClient = HttpClients.createDefault();

    }


    private CloseableHttpClient httpClient;

    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

}
