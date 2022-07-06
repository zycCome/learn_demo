package com.zyc.webclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Description TODO
 * @Author zilu
 * @Date 2022/6/28 6:39 PM
 * @Version 1.0.0
 **/
@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return route(GET("/time"), req -> timeHandler.getTime(req))
                // 这种方式相对于上一行更加简洁
                .andRoute(POST("/date"), timeHandler::getDate)
                .andRoute(GET("/web") , timeHandler::getWeb);
    }


}
