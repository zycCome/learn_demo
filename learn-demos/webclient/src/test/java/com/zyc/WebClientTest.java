package com.zyc;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author zyc66
 * @date 2024/12/27 14:46
 **/
@Slf4j
public class WebClientTest {


    @Test
    public void testMaxConnectionsOneService() throws InterruptedException {


        final int cpuCores = Runtime.getRuntime().availableProcessors();
        final int selectorCount = Math.max(cpuCores / 2, 4);
        final int workerCount = Math.max(cpuCores * 2, 8);
        final LoopResources pool = LoopResources.create("HCofSWC", selectorCount, workerCount, true);

        final Function<? super TcpClient, ? extends TcpClient> tcpMapper = tcp -> tcp
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_TIMEOUT, 10000)
                .runOn(pool);

        ConnectionProvider.Builder httpClientOfSWC = ConnectionProvider
                .builder("HttpClientOfSWC")
                // 设置最多有多少个连接
                .maxConnections(1)
                // 设置等待队列的大小
                .pendingAcquireMaxCount(10)
                // 控制最大等待时间
                .pendingAcquireTimeout(Duration.ofSeconds(6));
        final ConnectionProvider connectionProvider = httpClientOfSWC.build();

        final HttpClient hc = HttpClient.create(connectionProvider)
                .tcpConfiguration(tcpMapper);

        final Function<HttpClient, HttpClient> hcMapper = rhc -> rhc
                .compress(true);

        final WebClient.Builder wcb = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(hcMapper.apply(hc)));

        WebClient webClient = wcb.build();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String url = "http://localhost:8080/data";
                webClient
                        .method(HttpMethod.POST)
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject("{}"))
                        .exchange().subscribe(r -> {
                                    System.out.println(Thread.currentThread().getName());
                                    System.out.println(r.statusCode());
                                    r.bodyToMono(String.class).subscribe(t -> {
                                        log.info("success:{}",t);
                                    });
                                },
                                ex -> {
                                    log.error("异常");
                                    ex.printStackTrace();
                                });
            }).start();


        }

        Thread.sleep(30000);

    }

    @Test
    public void testMaxConnectionsOneService2() throws InterruptedException {


        final int cpuCores = Runtime.getRuntime().availableProcessors();
        final int selectorCount = Math.max(cpuCores / 2, 4);
        final int workerCount = Math.max(cpuCores * 2, 8);
        final LoopResources pool = LoopResources.create("HCofSWC", selectorCount, workerCount, true);

        final Function<? super TcpClient, ? extends TcpClient> tcpMapper = tcp -> tcp
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_TIMEOUT, 10000)
                .runOn(pool);

        ConnectionProvider.Builder httpClientOfSWC = ConnectionProvider
                .builder("HttpClientOfSWC")
                // 设置最多有多少个连接
                .maxConnections(5)
                // 设置等待队列的大小
                .pendingAcquireMaxCount(10)
                // 控制最大等待时间
                .pendingAcquireTimeout(Duration.ofSeconds(6));
        final ConnectionProvider connectionProvider = httpClientOfSWC.build();

        final HttpClient hc = HttpClient.create(connectionProvider)
                .tcpConfiguration(tcpMapper);

        final Function<HttpClient, HttpClient> hcMapper = rhc -> rhc
                .compress(true);

        final WebClient.Builder wcb = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(hcMapper.apply(hc)));

        WebClient webClient = wcb.build();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String url = "http://localhost:8080/data";
                webClient
                        .method(HttpMethod.POST)
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject("{}"))
                        .exchange().subscribe(r -> {
                                    System.out.println(Thread.currentThread().getName());
                                    System.out.println(r.statusCode());
                                    r.bodyToMono(String.class).subscribe(t -> {
                                        log.info("success:{}",t);
                                    });
                                },
                                ex -> {
                                    log.error("异常");
                                    ex.printStackTrace();
                                });
            }).start();


        }

        Thread.sleep(30000);

    }


    /**
     * 测试connects 小于 服务数量时的场景
     * 从表现来看，maxConnections 针对的是 Host
     * @throws InterruptedException
     */
    @Test
    public void testMaxConnectionsManyService() throws InterruptedException {


        final int cpuCores = Runtime.getRuntime().availableProcessors();
        final int selectorCount = Math.max(cpuCores / 2, 4);
        final int workerCount = Math.max(cpuCores * 2, 8);
        final LoopResources pool = LoopResources.create("HCofSWC", selectorCount, workerCount, true);

        final Function<? super TcpClient, ? extends TcpClient> tcpMapper = tcp -> tcp
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_TIMEOUT, 10000)
                .runOn(pool);

        ConnectionProvider.Builder httpClientOfSWC = ConnectionProvider
                .builder("HttpClientOfSWC")
                // 设置最多有多少个连接
                .maxConnections(1)
                // 设置等待队列的大小
                .pendingAcquireMaxCount(10)
                // 控制最大等待时间
                .pendingAcquireTimeout(Duration.ofSeconds(30));
        final ConnectionProvider connectionProvider = httpClientOfSWC.build();

        final HttpClient hc = HttpClient.create(connectionProvider)
                .tcpConfiguration(tcpMapper);

        final Function<HttpClient, HttpClient> hcMapper = rhc -> rhc
                .compress(true);

        final WebClient.Builder wcb = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(hcMapper.apply(hc)));

        WebClient webClient = wcb.build();
        for (int i = 0; i < 20; i++) {
            int port = 8080+i/4;
            String url = "http://localhost:"+port+"/data";
            new Thread(() -> {

                webClient
                        .method(HttpMethod.POST)
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject("{}"))
                        .exchange().subscribe(r -> {
                                    System.out.println(Thread.currentThread().getName());
                                    System.out.println(r.statusCode());
                                    r.bodyToMono(String.class).subscribe(t -> {
                                        log.info("success:{}",t);
                                    });
                                },
                                ex -> {
                                    log.error("异常");
                                    ex.printStackTrace();
                                });
            }).start();


        }

        Thread.sleep(30000);

    }

    /**
     * http 长连接时长对连接池复用的影响，测试边界场景下能否正常工作
     * timeout = n 是指空闲了多久，之后会关闭。不是从创建开始算的
     * 连接池在长连接超时后会自动移除失效连接，对应日志：
     * 2024-12-27 16:30:06.208 DEBUG   --- [  HCofSWC-nio-4] r.n.resources.PooledConnectionProvider   : [id: 0x9a39a616, L:/127.0.0.1:34463 ! R:localhost/127.0.0.1:9093] onStateChange(PooledConnection{channel=[id: 0x9a39a616, L:/127.0.0.1:34463 ! R:localhost/127.0.0.1:9093]}, [disconnecting])
     * 之后会新增连接
     * @throws InterruptedException
     */
    @Test
    public void testHttpKeepAlive() throws InterruptedException {


        final int cpuCores = Runtime.getRuntime().availableProcessors();
        final int selectorCount = Math.max(cpuCores / 2, 4);
        final int workerCount = Math.max(cpuCores * 2, 8);
        final LoopResources pool = LoopResources.create("HCofSWC", selectorCount, workerCount, true);

        final Function<? super TcpClient, ? extends TcpClient> tcpMapper = tcp -> tcp
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_TIMEOUT, 10000)
                .runOn(pool);

        ConnectionProvider.Builder httpClientOfSWC = ConnectionProvider
                .builder("HttpClientOfSWC")
                // 设置最多有多少个连接
                .maxConnections(1)
                // 设置等待队列的大小
                .pendingAcquireMaxCount(10)
                // 控制最大等待时间
                .pendingAcquireTimeout(Duration.ofSeconds(30));
        final ConnectionProvider connectionProvider = httpClientOfSWC.build();

        final HttpClient hc = HttpClient.create(connectionProvider)
                .tcpConfiguration(tcpMapper);

        final Function<HttpClient, HttpClient> hcMapper = rhc -> rhc
                .compress(true);

        final WebClient.Builder wcb = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(hcMapper.apply(hc)));

        WebClient webClient = wcb.build();
        for (int i = 0; i < 5; i++) {
            int port = 8080+i/4;
            String url = "http://localhost:"+9093+"/data/config1";
            new Thread(() -> {

                webClient
                        .method(HttpMethod.GET)
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(BodyInserters.fromObject("{}"))
                        .exchange().subscribe(r -> {
                                    System.out.println(Thread.currentThread().getName());
                                    System.out.println(r.statusCode());
                                    r.bodyToMono(String.class).subscribe(t -> {
                                        log.info("success:{}",t);
                                    });
                                },
                                ex -> {
                                    log.error("异常");
                                    ex.printStackTrace();
                                });
            }).start();
            Thread.sleep(10000);

        }

        Thread.sleep(30000);

    }


}
