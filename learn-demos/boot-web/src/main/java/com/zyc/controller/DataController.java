package com.zyc.controller;

import com.zyc.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyc66
 * @date 2024/11/11 22:21
 **/
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    int i = 0;

    @Autowired
    private ConfigProperties configProperties1;

    @GetMapping("/config1")
    public String config1() {
        System.out.println(configProperties1);
        log.info("this: {}, i:{}",this,i++);
        return "ok5";
    }


    @GetMapping(value = "/version", headers = "version=1")
    public String version1() {
        return "version1";
    }

    @GetMapping(value = "/version", headers = "version=2")
    public String version2() {
        return "version2";
    }
    @GetMapping(value = "/version")
    public String versionAll() {
        return "versionAll";
    }


}
