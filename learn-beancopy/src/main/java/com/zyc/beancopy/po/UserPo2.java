package com.zyc.beancopy.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/18 11:18 AM
 * @Version 1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPo2 {

    private Long id;
    private Date gmtCreate;
    private String createTime;
    private Long buyerId;
    private String age;
    private String userNick;
    private String userVerified;
    private String attributes;

}
