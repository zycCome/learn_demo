package com.zyc.learn_quartz.utils.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service
public class HelloSpringService {

    DataSource dataSource;

    @PostConstruct
    public void ds() {
        System.out.println("HelloSpringService -=-=-==-" + dataSource);
    }

    public String helloSpring() {
        return "hello spring";
    }
}
