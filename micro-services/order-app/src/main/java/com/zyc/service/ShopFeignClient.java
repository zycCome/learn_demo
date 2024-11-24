package com.zyc.service;

import com.zyc.entity.Shop;
import com.zyc.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zyc66
 * @date 2024/11/24 20:40
 **/
@FeignClient(value = "shop-app" ,path = "/shop")
public interface ShopFeignClient {


    @GetMapping("/list")
    CommonResult<List<Shop>> list();

    /**
     * 得到
     *
     * @param id id
     * @return {@code CommonResult<Order>}
     */
    @GetMapping("/get/{id}")
    CommonResult<Shop> get(@PathVariable("id") Long id);


}
