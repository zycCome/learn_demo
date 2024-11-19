package com.zyc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.api.DeptService;
import org.apache.dubbo.samples.api.UserService;
import org.apache.dubbo.samples.po.Dept;
import org.apache.dubbo.samples.po.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyc66
 * @date 2024/11/18 13:35
 **/
@RestController
@RequestMapping("/api/demo/dubbo")
@Slf4j
public class DeptController {

    @Value("${tag}")
    private String tag;

    @DubboReference
    DeptService deptService;

    @GetMapping("/dept/{deptId}")
    public Dept getByDeptId(@PathVariable("deptId")Integer deptId) {
        log.info("{} getByDeptId :{}",tag,deptId);
        return deptService.getByDeptId(deptId);
    }


}
