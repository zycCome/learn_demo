package com.zyc.nacos.course.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供者
 *
 * @author zhuyc
 * @date 2021/7/16 16:41
 */
@RestController
@RequestMapping("course")
public class CourseController {

    @GetMapping("list")
    public String list() {
        return "查询课程列表";
    }
}
