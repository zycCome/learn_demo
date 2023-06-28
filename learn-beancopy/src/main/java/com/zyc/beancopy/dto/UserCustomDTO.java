package com.zyc.beancopy.dto;

import lombok.Data;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/18 11:31 AM
 * @Version 1.0.0
 **/
@Data
public class UserCustomDTO {

    private String name;

    private int sex;

    private boolean married;

    private String birthday;

    private String regDate;

    private UserExtDTO userExtDTO;

    private String memo;


}
