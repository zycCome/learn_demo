package order.service;


import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.transaction.Propagation;
import order.model.Order;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@LocalTCC
public interface OrderService {

    boolean create(Order order);

    boolean create2RecordInLocalTransactional(Order order);

    boolean create2RecordNotInLocalTransactional(Order order);

    @GlobalTransactional(propagation = Propagation.NOT_SUPPORTED)
    @Transactional
    boolean create2RecordInLocalTransactionalAndNoGlobalTransaction(Order order);



    /*
    第一阶段的方法
    通过注解指定第二阶段的两个方法名
    BusinessActionContext 上下文对象，用来在两个阶段之间传递数据
    @BusinessActionContextParameter 注解的参数数据会被存入 BusinessActionContext
     */
    @TwoPhaseBusinessAction(name = "orderTccAction", commitMethod = "commitOrder", rollbackMethod = "rollbackOrder" ,useTCCFence = true)
    boolean prepareCreateOrder(BusinessActionContext businessActionContext,
                               @BusinessActionContextParameter(paramName = "orderId") Long orderId,
                               @BusinessActionContextParameter(paramName = "userId") Long userId,
                               @BusinessActionContextParameter(paramName = "productId") Long productId,
                               @BusinessActionContextParameter(paramName = "count") Integer count,
                               @BusinessActionContextParameter(paramName = "money") BigDecimal money);
    // 第二阶段 - 提交
    boolean commitOrder(BusinessActionContext businessActionContext);
    // 第二阶段 - 回滚
    boolean rollbackOrder(BusinessActionContext businessActionContext);



}
