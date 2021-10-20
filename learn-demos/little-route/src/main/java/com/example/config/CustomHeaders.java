package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转发的请求头
 *
 * @author zhuyc
 * @date 2021/10/15 17:02
 */
@RefreshScope
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomHeaders {

    private List<String> headers = new ArrayList<>();

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
