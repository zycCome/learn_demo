package com.example.service;

import com.example.config.CustomHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhuyc
 * @date 2021/10/15 16:31
 */

//@Component
public class MyProxyServlet extends ProxyServlet {

    @Autowired
    private CustomHeaders headers;


//    @Value("${proxy.target_url2}")
//    private String test;

    @Override
    protected HttpResponse doExecute(HttpServletRequest servletRequest, HttpServletResponse servletResponse, HttpRequest proxyRequest) throws IOException {
        for (String header : headers.getHeaders()) {
            if(header.contains(":")) {
                String[] split = header.split(":");
                proxyRequest.setHeader(split[0], split[1]);
            }
        }
        return super.doExecute(servletRequest, servletResponse, proxyRequest);
    }
}
