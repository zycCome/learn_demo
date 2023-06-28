package com.zyc;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description http客户端测试
 * @Author zilu
 * @Date 2022/7/4 8:41 PM
 * @Version 1.0.0
 **/
@Slf4j
public class HttpClientTest {

    int requestSize = 500;

    AtomicInteger success = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);

    private CloseableHttpClient httpclient;


    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("okhttp-pool-").build();

    /**
     * 同时允许的连接数
     */
    Semaphore concurrentConnections = new Semaphore(2000);


    @Test
    public void testOkhttp() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 200,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100000), threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(threadPoolExecutor))
                .retryOnConnectionFailure(true)
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(1000000);
        okHttpClient.dispatcher().setMaxRequests(1000000);

        CountDownLatch countDownLatch = new CountDownLatch(100);
        long startTime = System.currentTimeMillis();
        requestWithOkhttp(okHttpClient, null, 100, countDownLatch);
        countDownLatch.await();
        log.info("热身结束,耗时：" + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        countDownLatch = new CountDownLatch(requestSize);
        requestWithOkhttp(okHttpClient, null, requestSize, countDownLatch);
        countDownLatch.await();
        log.info("耗时：{},success:{},failed:{}", (System.currentTimeMillis() - startTime), success.get(), failed.get());

    }

    @Test
    public void testWebClient() throws InterruptedException {
        //配置固定大小连接池
        ConnectionProvider provider = ConnectionProvider
                .builder("tax-core")
                // 等待超时时间
                .pendingAcquireTimeout(Duration.ofSeconds(5))
                // 最大连接数
                .maxConnections(10000000)
                // 等待队列大小
                .pendingAcquireMaxCount(11)
                .build();

        LoopResources loop = LoopResources.create("kl-event-loop", 10, 10, true);
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                })
                .runOn(loop);

        HttpClient httpClient = HttpClient.create(provider);
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .codecs(configurer -> configurer
                                .defaultCodecs()
//                        .maxInMemorySize(10 * 1024 * 1024)
                )
                .build();


//        //配置动态连接池
//        //ConnectionProvider provider = ConnectionProvider.elastic("elastic pool");
//        //配置固定大小连接池，如最大连接数、连接获取超时、空闲连接死亡时间等
//        ConnectionProvider provider = ConnectionProvider.fixed("fixed", 1000000, 4000, Duration.ofSeconds(6));
//        HttpClient httpClient = HttpClient.create(provider)
////                .secure(sslContextSpec -> {
////                    SslContextBuilder sslContextBuilder = SslContextBuilder.forClient()
////                            .trustManager(new File("E://server.truststore"));
////                    sslContextSpec.sslContext(sslContextBuilder);
////                })
//                .tcpConfiguration(tcpClient -> {
//                    //指定Netty的select 和 work线程数量
//                    LoopResources loop = LoopResources.create("kl-event-loop", 1, 10, true);
//                    return tcpClient.doOnConnected(connection -> {
//                                //读写超时设置
//                                connection.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
//                                        .addHandlerLast(new WriteTimeoutHandler(10));
//                            })
//                            //连接超时设置
//                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
//                            .option(ChannelOption.TCP_NODELAY, true)
//                            .runOn(loop);
//                });
//
//
//        WebClient webClient = WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//        System.out.println(Runtime.getRuntime().maxMemory());


        CountDownLatch countDownLatch = new CountDownLatch(100);
        long startTime = System.currentTimeMillis();
        requestWithWebClient(webClient, 100, countDownLatch);
        countDownLatch.await();
        log.info("热身结束,耗时：" + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        countDownLatch = new CountDownLatch(requestSize);
        requestWithWebClient(webClient, requestSize, countDownLatch);
        countDownLatch.await();
        log.info("耗时：{},success:{},failed:{}", (System.currentTimeMillis() - startTime), success.get(), failed.get());

    }


    @Test
    public void testHttpClient() throws InterruptedException {
        initialHttpClient();


//        httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
//        httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100000), threadFactory);


        CountDownLatch countDownLatch = new CountDownLatch(100);
        long startTime = System.currentTimeMillis();
        requestWithHttpClient(100, countDownLatch, threadPoolExecutor);
        countDownLatch.await();
        log.info("热身结束,耗时：" + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        countDownLatch = new CountDownLatch(requestSize);
        requestWithHttpClient(requestSize, countDownLatch, threadPoolExecutor);
        countDownLatch.await();
        log.info("耗时：{},success:{},failed:{}", (System.currentTimeMillis() - startTime), success.get(), failed.get());

    }


    private void requestWithOkhttp(OkHttpClient okHttpClient, RequestBody body, int requestSize, CountDownLatch countDownLatch) throws InterruptedException {
        for (int i = 0; i < requestSize; i++) {
            concurrentConnections.acquire();
            RequestBody body2 = RequestBody.create(
                    MediaType.parse("application/json"), "{}");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/date")
                    .post(body2)
                    .build();


            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failed.incrementAndGet();
                    concurrentConnections.release();
                    countDownLatch.countDown();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException { //不在ui线程
                    String str = response.body().string();
                    success.incrementAndGet();
                    concurrentConnections.release();
                    countDownLatch.countDown();
                }
            });
        }
    }


    private void requestWithWebClient(WebClient webClient, int requestSize, CountDownLatch countDownLatch) throws InterruptedException {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100,
//                60L, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(100000), threadFactory);
//        Scheduler scheduler = Schedulers.fromExecutor(threadPoolExecutor);
        for (int i = 0; i < requestSize; i++) {
            concurrentConnections.acquire();
            String url = "http://localhost:8080/date";
//            if((i & 1) == 0) {
//                url = "http://localhost:8080/date";
//            }
            webClient
                    .method(HttpMethod.POST)
                    .uri(url)
                    .body(BodyInserters.fromObject("{}"))
                    .exchange().subscribe(r -> {
//                        System.out.println(Thread.currentThread().getName());
//                        System.out.println(r.statusCode());
//                        r.bodyToMono(String.class).subscribeOn().subscribe(e -> {
//                            Integer a = null;
////                            log.info(e);
//                        });

//                        log.info(String.valueOf(success.incrementAndGet()));
                        success.incrementAndGet();
                        countDownLatch.countDown();
                        concurrentConnections.release();

                    },
                    ex -> {
                        failed.incrementAndGet();
                        countDownLatch.countDown();
                        ex.printStackTrace();
                        concurrentConnections.release();
                    });
        }


    }


    private void requestWithHttpClient(int requestSize, CountDownLatch countDownLatch, ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
        for (int i = 0; i < requestSize; i++) {
            concurrentConnections.acquire();
            threadPoolExecutor.submit(() -> {
                        CloseableHttpResponse response = null;
                        try {
                            HttpPost httpPost = new HttpPost("http://localhost:8080/date");
                            httpPost.setEntity(new StringEntity("{}", Consts.UTF_8));

                            //执行请求
                            response = httpclient.execute(httpPost);
                            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                                HttpEntity entity = response.getEntity();
                                countDownLatch.countDown();
                                concurrentConnections.release();
//                                log.info(String.valueOf(success.incrementAndGet()));
                                success.incrementAndGet();
                                // If the response does not enclose an entity, there is no need
                                // to bother about connection release
                                if (entity != null) {
                                    InputStream instream = entity.getContent();
                                    try {
                                        instream.read();
                                        // do something useful with the response
                                    } catch (IOException ex) {
                                        // In case of an IOException the connection will be released
                                        // back to the connection manager automatically
                                        throw ex;
                                    } finally {
                                        // Closing the input stream will trigger connection release
                                        // 释放连接回到连接池
                                        instream.close();
                                    }
                                }
                            } else {
                                concurrentConnections.release();
                                countDownLatch.countDown();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            concurrentConnections.release();
                            countDownLatch.countDown();
                            failed.incrementAndGet();
                        } finally {
                            if (response != null) {
                                try {
                                    //关闭连接(如果已经释放连接回连接池，则什么也不做)
                                    response.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
        }
    }

    public void initialHttpClient(){
        // HttpClient跳过https证书认证 https://blog.csdn.net/MrHamster/article/details/94030969
        SSLContext sslContext = getSSLContext();
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslContext)).build();


        /**
         * 创建连接管理器，并设置相关参数
         */
        //连接管理器，使用无惨构造
        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        /**
         * 连接数相关设置
         */
        //最大连接数
        connManager.setMaxTotal(10000);
        //默认的每个路由的最大连接数
        connManager.setDefaultMaxPerRoute(10000);


        /**
         * socket配置（默认配置 和 某个host的配置）
         */
//        SocketConfig socketConfig = SocketConfig.custom()
//                .setTcpNoDelay(true)     //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
//                .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
//                .setSoTimeout(5000)       //接收数据的等待超时时间，单位ms
////                .setSoLinger(60)         //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
//                .setSoKeepAlive(true)    //开启监视TCP连接是否有效
//                .build();
//        connManager.setDefaultSocketConfig(socketConfig);
//        connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);

        /**
         * HTTP connection相关配置（默认配置 和 某个host的配置）
         * 一般不修改HTTP connection相关配置，故不设置
         */
        //消息约束
        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200)
                .setMaxLineLength(2000)
                .build();
        //Http connection相关配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints)
                .build();
        //一般不修改HTTP connection相关配置，故不设置
        //connManager.setDefaultConnectionConfig(connectionConfig);
        //connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

        /**
         * request请求相关配置
         */
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)         //连接超时时间
                .setSocketTimeout(5 * 1000)          //读超时时间（等待数据超时时间）
                .setConnectionRequestTimeout(1000)    //从池中获取连接超时时间
                .setStaleConnectionCheckEnabled(false)//检查是否为陈旧的连接，默认为true，类似testOnBorrow
                .build();

        /**
         * 重试处理
         * 默认是重试3次
         */
        //禁用重试(参数：retryCount、requestSentRetryEnabled)
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);
        //自定义重试策略
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                //Do not retry if over max retry count
                if (executionCount >= 1) {
                    return false;
                }
                //Timeout
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                //Unknown host
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                //Connection refused
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                //SSL handshake exception
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                //Retry if the request is considered idempotent
                //如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
                //HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
                //而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
                //Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
                if (idempotent) {
                    return true;
                }

                return true;
            }
        };

        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = 60000;
                }
                return keepAlive;
            }
        };





        /**
         * 创建httpClient
         */
        httpclient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrategy)
                .setConnectionManager(connManager)             //连接管理器
//                .setProxy(new HttpHost("myproxy", 8080))       //设置代理
                .setDefaultRequestConfig(defaultRequestConfig) //默认请求配置
//                .setRetryHandler(myRetryHandler)               //重试策略
                .build();

//        //创建HttpClient
//        httpClient = HttpClients.custom()
//                .setConnectionManager(manager)
//                .setConnectionManagerShared(false)  //连接池不是共享模式
//                .evictIdleConnections(60, TimeUnit.SECONDS)  //定期回收空闲连接
//                .evictExpiredConnections()  //定期回收过期连接
//                .setConnectionTimeToLive(60,TimeUnit.SECONDS)   //连接存活时间，如果不设置，则根据长连接信息决定
//                .setDefaultRequestConfig(defaultRequestConfig)  //设置默认请求配置
//                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)    //连接重用策略，即是否能keepAlive
//                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)  //长连接配置，即获取长连接生产多长时间
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0,false))
//                //设置重试次数，默认是3次；当前是禁用掉
//                .build();

    }

    private static SSLContext getSSLContext() {
        try {
            // 这里可以填两种值 TLS和LLS , 具体差别可以自行搜索
            SSLContext sc = SSLContext.getInstance("TLS");
            // 构建新对象
            X509TrustManager manager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                // 这里返回Null
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[]{manager}, null);
            return sc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
