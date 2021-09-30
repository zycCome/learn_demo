package com.zyc.boot.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 开发任务
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("develop_task")
@ApiModel(value="DevelopTask对象", description="开发任务")
public class DevelopTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "开发任务名称")
    private String name;

    @ApiModelProperty(value = "开发任务appKey")
    private String appKey;

    @ApiModelProperty(value = "开发任务appSecret")
    private String appSecret;

    @ApiModelProperty(value = "最大支持项目数量")
    private Integer projectNumber;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatedAt;


}
