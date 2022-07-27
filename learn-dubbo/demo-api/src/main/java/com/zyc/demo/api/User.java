package com.zyc.demo.api;

import java.io.Serializable;

/**
 * @Description
 * @Author zilu
 * @Date 2022/7/21 11:51 AM
 * @Version 1.0.0
 **/
public class User implements Serializable {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
