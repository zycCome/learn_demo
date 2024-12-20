package storage.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@TableName(value = "tab_storage")
@Data
@Accessors(chain = true)
public class Storage {

    @TableId
    private Long id;

    private Long total;

    private Long productId;

    private Long used;

}
