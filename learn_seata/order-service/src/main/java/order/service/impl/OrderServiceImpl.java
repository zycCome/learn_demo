package order.service.impl;


import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.transaction.Propagation;
import lombok.extern.slf4j.Slf4j;
import order.mapper.OrderMapper;
import order.model.Order;
import order.service.OrderService;
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

    @Override

    public boolean create(Order order) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        log.info("创建订单开始");
        int index = orderMapper.insert(order);
        log.info("创建订单结束");
        return index > 0;
    }


    @Override
    @Transactional
    public boolean create2RecordInLocalTransactional(Order order) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        log.info("创建订单开始");
        order.setId(null);
        int index = orderMapper.insert(order);
        order.setId(null);
        int index2 = orderMapper.insert(order);

        log.info("创建订单结束");
        return index > 0;
    }


    @Override
    public boolean create2RecordNotInLocalTransactional(Order order) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        log.info("创建订单开始");
        order.setId(null);
        int index = orderMapper.insert(order);
        order.setId(null);
        int index2 = orderMapper.insert(order);

        log.info("创建订单结束");
        return index > 0;
    }


    @Override
    @GlobalTransactional(propagation = Propagation.NOT_SUPPORTED)
    @Transactional
    public boolean create2RecordInLocalTransactionalAndNoGlobalTransaction(Order order) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        log.info("创建订单开始");
        order.setId(null);
        int index = orderMapper.insert(order);
        order.setId(null);
        int index2 = orderMapper.insert(order);

        log.info("创建订单结束");
        return index > 0;
    }
}
