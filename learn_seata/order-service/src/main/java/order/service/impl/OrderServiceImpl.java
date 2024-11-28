package order.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.transaction.Propagation;
import lombok.extern.slf4j.Slf4j;
import order.mapper.OrderMapper;
import order.model.Order;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean prepareCreateOrder(BusinessActionContext businessActionContext, Long orderId, Long userId, Long productId, Integer count, BigDecimal money) {
        log.info("prepareCreateOrder inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        log.info("创建 order 第一阶段，预留资源 - "+businessActionContext.getXid());
        //因为orderId是唯一的，不能重复执行，满足幂等性， 创建状态为0（创建中）的订单
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setStatus(0);
        order.setProductId(productId);
        order.setCount(count);
        order.setMoney(money);
        orderMapper.insert(order);

        // TODO 防止悬挂
        //事务成功，保存一个标识，供第二阶段进行判断
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean commitOrder(BusinessActionContext businessActionContext) {
        log.info("创建 order 第二阶段提交，修改订单状态1 - "+businessActionContext.getXid());
        // TODO 幂等性，如果commit阶段重复执行则直接返回
        long orderId = Long.parseLong(businessActionContext.getActionContext("orderId").toString());
        //确认提交，将订单状态修改为1（创建完成）


        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status",1);
        updateWrapper.eq("id",orderId);
        orderMapper.update(null,updateWrapper);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean rollbackOrder(BusinessActionContext businessActionContext) {
        log.info("创建 order 第二阶段回滚，删除订单 - "+businessActionContext.getXid());
        //第一阶段没有完成的情况下，不必执行回滚（空回滚处理）
        //因为第一阶段有本地事务，事务失败时已经进行了回滚。
        //如果这里第一阶段成功，而其他全局事务参与者失败，这里会执行回滚
        //TODO 幂等性控制：如果重复执行回滚则直接返回
        //TODO 悬挂处理
        //创建识别，执行Cancel操作，删除临时订单
        long orderId = Long.parseLong(businessActionContext.getActionContext("orderId").toString());
        orderMapper.deleteById(orderId);
        return true;
    }
}
