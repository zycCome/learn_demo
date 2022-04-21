package com.zyc.mybatis.pojo;

import java.io.Serializable;

/**
 * @author zhuyc
 * @date 2022/04/21 12:38
 **/
public class User implements Serializable {
    private static final long serialVersionUID = 8596421348439484783L;
    private int id;
    private String userName;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
