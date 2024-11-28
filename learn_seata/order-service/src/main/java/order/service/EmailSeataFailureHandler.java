package order.service;

import io.seata.tm.api.DefaultFailureHandlerImpl;
import io.seata.tm.api.GlobalTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Seata 分布式事物失败的处理
 * @author zyc66
 * @date 2024/11/28 14:48
 **/
@Component("failureHandler")
@Slf4j
public class EmailSeataFailureHandler extends DefaultFailureHandlerImpl {

    @Override
    public void onBeginFailure(GlobalTransaction tx, Throwable cause) {
        super.onBeginFailure(tx, cause);
        log.error("邮件通知:分布式事物出现异常:[onBeginFailure],xid:[{}]", tx.getXid());
    }

    @Override
    public void onCommitFailure(GlobalTransaction tx, Throwable cause) {
        super.onCommitFailure(tx, cause);
        log.error("邮件通知:分布式事物出现异常:[onCommitFailure],xid:[{}]", tx.getXid());
    }

    @Override
    public void onRollbackFailure(GlobalTransaction tx, Throwable originalException) {
        super.onRollbackFailure(tx, originalException);
        log.error("邮件通知:分布式事物出现异常:[onRollbackFailure],xid:[{}]", tx.getXid());
    }

    @Override
    public void onRollbackRetrying(GlobalTransaction tx, Throwable originalException) {
        super.onRollbackRetrying(tx, originalException);
        log.error("邮件通知:分布式事物出现异常:[onRollbackRetrying],xid:[{}]", tx.getXid());
    }
}

