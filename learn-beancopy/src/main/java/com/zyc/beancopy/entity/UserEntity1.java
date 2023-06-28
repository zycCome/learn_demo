package com.zyc.beancopy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity1 {
    private Long id;
    private Date gmtCreate;
    private Date createTime;
    private Long buyerId;
    private Long age;

    /**
     * 属性类型相同名称不同
     */
    private String userNick1;
    private String userVerified;
}
