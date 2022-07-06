package com.zyc.webclient.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TimeHandler {
    public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

    public Mono<ServerResponse> getDate(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).delayElement(Duration.ofMillis(200)), String.class);
    }

    private WebClient webClient = WebClient.builder()
            .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(10 * 1024 * 1024)).build();

    /**
     * 有问题
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getWeb(ServerRequest serverRequest) {
        AtomicReference<String> reference = new AtomicReference<>();
        CountDownLatch countDownLatch =  new CountDownLatch(1);
        new Thread(() -> {
            Mono resp = webClient.get()
                    .uri("http://www.baidu.com")
//                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .exchange();
            ClientResponse response = (ClientResponse) resp.block();
            String resource = response.bodyToMono(String.class).block();
            reference.set(resource);
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ok().contentType(MediaType.TEXT_HTML).body(Mono.just(reference.get()), String.class);
    }
}
