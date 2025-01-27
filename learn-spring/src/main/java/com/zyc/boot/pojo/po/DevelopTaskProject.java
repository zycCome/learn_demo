package com.zyc.boot.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 开发任务和项目关联关系表
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("develop_task_project")
@ApiModel(value="DevelopTaskProject对象", description="开发任务和项目关联关系表")
public class DevelopTaskProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID")
    @TableId(value = "project_id", type = IdType.AUTO)
    private Long projectId;

    @ApiModelProperty(value = "开发任务ID")
    private Integer taskId;


}
