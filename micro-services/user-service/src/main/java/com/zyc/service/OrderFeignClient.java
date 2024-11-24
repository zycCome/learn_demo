package com.zyc.service;


import com.zyc.entity.Order;
import com.zyc.entity.User;
import com.zyc.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "order-service" ,path = "/order")
public interface OrderFeignClient {


    @GetMapping("/list")
    CommonResult<List<Order>> list();

    /**
     * 得到
     *
     * @param id id
     * @return {@code CommonResult<Order>}
     */
    @GetMapping("/get/{id}")
    CommonResult<Order> get(@PathVariable("id") Long id);


}
