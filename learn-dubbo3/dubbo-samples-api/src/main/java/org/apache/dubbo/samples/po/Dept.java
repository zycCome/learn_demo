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
public class Dept implements Serializable {

    private Integer deptId;

    private String  deptName;


}
