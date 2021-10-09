package order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import order.model.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Mapper
public interface OrderMapper  extends BaseMapper<Order> {

}
