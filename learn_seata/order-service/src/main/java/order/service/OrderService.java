package order.service;


import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.transaction.Propagation;
import order.model.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
public interface OrderService {

    boolean create(Order order);

    boolean create2RecordInLocalTransactional(Order order);

    boolean create2RecordNotInLocalTransactional(Order order);

    @GlobalTransactional(propagation = Propagation.NOT_SUPPORTED)
    @Transactional
    boolean create2RecordInLocalTransactionalAndNoGlobalTransaction(Order order);
}
