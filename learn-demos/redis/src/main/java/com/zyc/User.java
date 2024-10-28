package com.zyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyc66
 * @date 2024/10/27 17:17
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;

    private String name;

    private int age;
}
