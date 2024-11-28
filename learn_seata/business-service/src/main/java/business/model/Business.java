package business.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@TableName(value = "tab_business")
@Data
@Accessors(chain = true)
public class Business {

    @TableId
    private Long id;

    private String message;


    private Integer version;

}
