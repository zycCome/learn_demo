package com.example.config;

import com.example.service.MyProxyServlet;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * @author zhuyc
 * @date 2021/10/15 16:23
 */
//@Configuration
public class ProxyServletConfiguration {
    /**
     * 读取配置文件中路由设置
     */
    @Value("${proxy.servlet_url}")
    private String servlet_url;
    /**
     * 读取配置中代理目标地址
     */
    @Value("${proxy.target_url}")
    private String target_url;

    @Bean
    public Servlet createProxyServlet() {
        /** 创建新的ProxyServlet */
        return new MyProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), servlet_url);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of("targetUri", target_url, "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }

    /**
     * fix springboot中使用proxyservlet的 bug.
     * https://github.com/mitre/HTTP-Proxy-Servlet/issues/83
     * https://stackoverflow.com/questions/8522568/why-is-httpservletrequest-inputstream-empty
     * @param filter
     * @return 关闭springboot 自带的 HiddenHttpMethodFilter 防止post提交的form数据流被提前消费
     */
//    @Bean
//    public FilterRegistrationBean registration(HiddenHttpMethodFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//        registration.setEnabled(false);
//        return registration;
//    }
}
