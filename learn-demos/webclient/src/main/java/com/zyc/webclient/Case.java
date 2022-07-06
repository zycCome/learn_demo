package com.zyc.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyc.webclient.controller.Person;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 参考资料：
 * 1.https://cloud.tencent.com/developer/article/1793360
 *
 *
 * @Description WebClient测试
 * @Author zilu
 * @Date 2022/6/27 5:34 PM
 * @Version 1.0.0
 **/
public class Case {

    private WebClient webClient;

    @Before
    public void before() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });

        webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    /**
     * 测试获取网页
     */
    @Test
    public void testGetPage() throws InterruptedException {

        /*
        exchange方法提供了ClientResponse以及它的status和header，而retrieve方法是直接获取body的内容
         */
        Mono<ClientResponse> res = WebClient
                .create()
                .method(HttpMethod.GET)
                .uri("http://192.168.4.91:9098/api/dev/services")
                .exchange();
        ClientResponse clientResponse = res.block();
        System.out.println(clientResponse);
        Resource resource = clientResponse.bodyToMono(Resource.class).block();
        System.out.println(resource);

        Thread.sleep(10000);
        System.out.println("end");


    }

    private Flux<String> getZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.two two two";
        return Flux.fromArray(desc.split("\\s+"));  // 1
    }

    /**
     * 测试zip
     * 结论：其中一个流终止，则整个流终止
     * @throws InterruptedException
     */
    @Test
    public void testSimpleOperators() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);  // 2
        Flux.zip(
                        getZipDescFlux(),
                        Flux.interval(Duration.ofMillis(200)))  // 3
                .subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);    // 4
        countDownLatch.await(10, TimeUnit.SECONDS);     // 5
        System.out.println("subscribe end");
        Thread.sleep(100000);
    }


    /**
     * 注意：流在报错后就终止了，所有的错误处理并不能改变这个终止结果。因此如果后面还有元素，也不会被消费了。
     */
    @Test
    public void testOnErrorResume() {
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .onErrorResume(e -> Mono.just(new Random().nextInt(6))) // 提供新的数据流
                .map(i -> i*i)
                .subscribe(System.out::println, System.err::println);
    }

    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Person person = new Person();
        person.setName("Tom");
        person.setAge(40);
        System.out.println("序列化");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
        System.out.println(jsonString);
        System.out.println("反序列化");
        jsonString = "{\n" +
                "\"name\":\"xiaoming\",\n" +
                "\"age\":18,\n" +
                "\"sex\":\"男\"\n" +
                "\n" +
                "\n" +
                "}";
        Person deserializedPerson = objectMapper.readValue(jsonString, Person.class);
        System.out.println(deserializedPerson);

    }


}
