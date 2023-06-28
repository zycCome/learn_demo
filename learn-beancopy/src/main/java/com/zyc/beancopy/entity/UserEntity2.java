package com.zyc.beancopy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity2 {
    private Long id;
    private Date gmtCreate;
    private Date createTime;
    private Long buyerId;
    private Long age;
    private String userNick1;
    private String userVerified;
    private Attributes attributes;
}
