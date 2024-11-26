package com.zyc.controller;

import com.zyc.entity.Order;
import com.zyc.entity.User;
import com.zyc.mapper.UserMapper;
import com.zyc.result.CommonResult;
import com.zyc.result.ResultUtils;
import com.zyc.service.OrderFeignClient;
import com.zyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
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


    @Autowired
    private UserService userService;

    @GetMapping("/listOrder")
    public CommonResult<List<Order>> listOrder() {
        CommonResult<List<Order>> orderList = orderService.list();
        return orderList;
    }

    @GetMapping("/getOrder/{id}")
    public CommonResult<Order> getOrder(@PathVariable("id") Long id) {

        userService.api1();

        userService.openTrace();

        ActiveSpan.tag("control_tag", "my_value");
        ActiveSpan.error("Controller-Test-Error-Reason");

        User user = userMapper.selectByPrimaryKey(id);
        log.info("selectUser:"+user);
        userService.checkUser("zilu","123456");
        userService.postUser("xx","xxx");
        return orderService.get(id);
    }



}
