package com.zyc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyc66
 * @date 2024/11/24 14:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private String orderName;

}
