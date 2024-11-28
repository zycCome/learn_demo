package com.zyc.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@TableName(value = "tab_account")
@Data
@Accessors(chain = true)
public class Account {

    @TableId
    private Long id;

    @TableField("user_id")
    private Long userId;

    private BigDecimal money;


}
