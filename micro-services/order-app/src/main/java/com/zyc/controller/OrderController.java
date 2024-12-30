package com.zyc.controller;

import com.zyc.entity.Order;
import com.zyc.result.CommonResult;
import com.zyc.result.ResultUtils;
import com.zyc.service.ShopFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyc66
 * @date 2024/11/24 14:45
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private ShopFeignClient shopFeignClient;

    @GetMapping("/list")
    public CommonResult<List<Order>> list() {
        log.info("list");
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(111L,"衣服"));
        orderList.add(new Order(998L,"零食"));
        return ResultUtils.success(orderList);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Order> get(@PathVariable Long id) {
        log.info("get:{}",id);
        log.info("orderFeignClient.get:{}", shopFeignClient.get(id));
        return ResultUtils.success(new Order(1L,"数码"));
    }

    @GetMapping("/sleep/{duration}")
    public CommonResult<String> sleep(@PathVariable("duration") Long duration) {
        log.info("sleep:{}",duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResultUtils.success("sleep "+duration+" end");
    }


}
