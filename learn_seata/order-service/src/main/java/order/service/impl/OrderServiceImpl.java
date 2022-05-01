package order.service.impl;


import lombok.extern.slf4j.Slf4j;
import order.mapper.OrderMapper;
import order.mapper.OrderMapper2;
import order.model.Order;
import order.model.Order2;
import order.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderMapper2 orderMapper2;

    @Override
    @Transactional
    public boolean create(Order order) {
        log.info("创建订单开始");

//        //1.测试本地事务是否生效. 结论：1.不传递xid时，本地事务生效  2.传递xid时，该方法也在事务提交
//        //2.测试全局事务是否生效，以及代理连接什么时候生效，什么时候提交？
//        Order2 order2 = new Order2();
//        BeanUtils.copyProperties(order,order2);
//        orderMapper2.insert(order2);
//        if(order.getUserId().equals(1000L)) {
//            //分别测试传和不传xid的场景。不传xid：本地事务回滚； 传xid：本地事务也回滚
//            throw new RuntimeException("userId equals 1000");
//        }

//        int index = orderMapper.insert(order);
        int index = orderMapper.insert(order);
        log.info("创建订单结束");
        return index > 0;
    }
}
