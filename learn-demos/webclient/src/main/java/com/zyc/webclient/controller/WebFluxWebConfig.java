package com.zyc.webclient.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @Description TODO
 * @Author zilu
 * @Date 2022/6/28 7:53 PM
 * @Version 1.0.0
 **/
@Configuration
@EnableWebFlux
public class WebFluxWebConfig implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024);
    }
}

