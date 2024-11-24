package com.zyc.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * shop
 * @author 
 */
@Data
public class Shop implements Serializable {
    private Integer id;

    private String shopName;

    private String address;

    private String email;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}