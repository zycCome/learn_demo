package com.zyc;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/5 5:55 PM
 * @Version 1.0.0
 **/
@Slf4j
public class FeatureTest {

    private CloseableHttpClient httpClient;

    /**
     * 测试线性连接池满的场景
     * 1. 池大小设置为1
     * 2. 同一个url请求两次，观察是否会复用，能否正常  请求复用
     * 3. 用新的url请求，观察现象  释放连接，生成新的请求
     */
    @Test
    public void testPoolFull() throws IOException {
        initial();
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/a.tar.gz",0);
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10.tar.gz","/Users/zilu/Desktop/b.tar.gz",0);
        download("https://mirrors.tuna.tsinghua.edu.cn/apache//httpd/httpd-2.4.57.tar.gz","/Users/zilu/Desktop/c.tar.gz",0);
    }


    /**
     * 测试并发连接池满的场景
     *
     * 现象：thread2 报错 org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool  符合预期
     * @throws IOException
     */
    @Test
    public void testPoolFullWhenConcurrent() throws IOException, InterruptedException {
        initial();
        new Thread() {
            @Override
            public void run() {
                try {
                    download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/a.tar.gz",0);
                } catch (IOException e) {
                    log.error("失败1",e);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10.tar.gz","/Users/zilu/Desktop/b.tar.gz",0);
                } catch (IOException e) {
                    log.error("失败2",e);
                }
            }
        }.start();

        Thread.sleep(100000);
//        download("https://mirrors.tuna.tsinghua.edu.cn/apache//httpd/httpd-2.4.57.tar.gz","/Users/zilu/Desktop/c.tar.gz");
    }


    /**
     * 测试硬中断：httpClient无法设置整个请求的超时时间，只能设置连接超时时间，或者响应超时时间
     *
     * 1. 请求url，硬中断。 成功
     * 2. 请求同一个url，观察是否复用连接，如果复用，连接能否正常使用？  结论：不复用，关闭之前的socket连接，新建连接，新连接正常使用
     */
    @Test
    public void testHardTimeOut() throws IOException {
        initial();
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/a.tar.gz",2);
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/a1.tar.gz",0);
    }


    /**
     * 测试请求完成后，中断HttpGet 会有什么效果。比如connect是否还能复用，或者对正在复用的connect有什么影响
     * 现象：第一个请求结束后，第二个请求发起前对第二个请求无影响，仍然可以正常使用（并且复用了）
     * 现象：第一个请求结束后，第二个请求发起后 对第二个请求无影响，仍然可以正常使用（并且复用了）
     * @throws IOException
     */
    @Test
    public void testHardTimeOutAfterCompleted() throws IOException {
        initial();
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/d.tar.gz",5);
        download("https://downloads.apache.org/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz","/Users/zilu/Desktop/d1.tar.gz",0);
    }

    public void download(String url,String path,int timeout) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {

            // 执行请求
            FileOutputStream fos=  null ;
            if(timeout > 0) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (httpGet != null) {
                                httpGet.abort();
                            }
                        } catch (Exception e) {
                            log.error("硬中断异常",e);
                        }
                    }
                };
                new Timer(true).schedule(task, timeout * 1000);
            }

            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //请求体内容
                HttpEntity entity = response.getEntity();
                fos =  new FileOutputStream(path);
                fos.write(EntityUtils. toByteArray(entity));         //写入到磁盘
                log.info("url:{} ,path:{} 下载成功",url,path);
                fos.flush();
                fos.close();
            } else {
                log.error("url:{} ,path:{} 下载失败",url,path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下载异常",e);
        }
        finally {
            if (response != null) {
                response.close();
            }
            //相当于关闭浏览器
//            httpClient.close();
        }
        log.info("--------------------------------------");
    }

    public void initial() {
        /**
         * 创建连接管理器，并设置相关参数
         */
        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager();

        /**
         * 连接数相关设置
         */
        //最大连接数
        connManager.setMaxTotal(1);
        //默认的每个路由的最大连接数
        connManager.setDefaultMaxPerRoute(1);

        /**
         * socket配置（默认配置 和 某个host的配置）
         */
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)     //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoTimeout(5000)       //接收数据的等待超时时间，单位ms
//                .setSoLinger(60)         //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
                .setSoKeepAlive(true)    //开启监视TCP连接是否有效
                .build();
        connManager.setDefaultSocketConfig(socketConfig);


        /**
         * request请求相关配置
         */
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)         //连接超时时间
                .setSocketTimeout(5000 )          //读超时时间（等待数据超时时间）
                .setConnectionRequestTimeout(1000)    //从池中获取连接超时时间
                .build();

        /**
         * 重试处理
         * 默认是重试3次
         */
        //禁用重试(参数：retryCount、requestSentRetryEnabled)
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);

        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = 20000;
                }
                System.out.println("keepAlive:"+keepAlive);
                return keepAlive;
            }
        };


        /**
         * 创建httpClient
         */
        httpClient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrategy)
                .setConnectionManager(connManager)             //连接管理器
                .setDefaultRequestConfig(defaultRequestConfig) //默认请求配置
                .build();
    }

}
