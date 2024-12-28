package com.zyc.beancopy.entity;

import com.zyc.beancopy.po.Address;
import com.zyc.beancopy.po.NestedObject;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserEntity {
    private Long id;
    private Date gmtCreate;
    private Date createTime;
    private Long buyerId;
    private Long age;
    private String userNick;
    private String userVerified;

    private Address address;

    private List<Integer> integerList2;

    private NestedObject nestedObject;
}
