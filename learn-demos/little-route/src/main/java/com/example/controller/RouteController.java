package com.example.controller;

import com.example.service.RoutingDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 路由服务
 *
 * @author zhuyc
 * @date 2021/10/15 15:18
 */
@RestController
@RequestMapping("/")
@RefreshScope //动态刷新配置，重要
public class RouteController {

    @Value("${test.fresh.value:default}")
    private String value;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RoutingDelegate routingDelegate;

    @RequestMapping("value")
    public String value() {
        return value;
    }
//
//    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
//    public ResponseEntity catchAll(HttpServletRequest request, HttpServletResponse response) {
//        ResponseEntity<String> responseEntity = routingDelegate.redirect(request, response, "http://10.30.30.50:38080", "");
//        return responseEntity;
//    }

    @RequestMapping("/qw/callback")
    public String callback() {
        System.out.println("111");
        return "success";
    }

}
