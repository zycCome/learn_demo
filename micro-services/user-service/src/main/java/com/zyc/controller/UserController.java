package com.zyc.controller;

import com.zyc.entity.Order;
import com.zyc.entity.User;
import com.zyc.mapper.UserMapper;
import com.zyc.result.CommonResult;
import com.zyc.result.ResultUtils;
import com.zyc.service.OrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zyc66
 * @date 2024/11/24 14:28
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    OrderFeignClient orderService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/listOrder")
    public CommonResult<List<Order>> listOrder() {
        CommonResult<List<Order>> orderList = orderService.list();
        return orderList;
    }

    @GetMapping("/getOrder/{id}")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        log.info("selectUser:"+user);
        return orderService.get(id);
    }



}
