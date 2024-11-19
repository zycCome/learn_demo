package org.apache.dubbo.samples.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zyc66
 * @date 2024/11/18 13:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer userId;

    private String  userName;


}
