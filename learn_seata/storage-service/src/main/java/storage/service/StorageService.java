package storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@LocalTCC
public interface StorageService {

    boolean updateUseNum(long productId , long used);


    @TwoPhaseBusinessAction(name = "storageTccAction", commitMethod = "commitUpdateUseNum", rollbackMethod = "rollbackUpdateUseNum")
    boolean prepareUpdateUseNum(BusinessActionContext businessActionContext, @BusinessActionContextParameter(paramName = "productId") long productId , @BusinessActionContextParameter(paramName = "used") long used);
    boolean commitUpdateUseNum(BusinessActionContext businessActionContext);
    boolean rollbackUpdateUseNum(BusinessActionContext businessActionContext);

}
