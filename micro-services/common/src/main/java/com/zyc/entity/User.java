package com.zyc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zyc66
 * @date 2024/11/24 14:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
