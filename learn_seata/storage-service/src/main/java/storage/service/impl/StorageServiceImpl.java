package storage.service.impl;


import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storage.mapper.StorageMapper;
import storage.model.Storage;
import storage.service.StorageService;

import java.util.List;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @Transactional
    public boolean updateUseNum(long productId, long used) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());
        if (used > 10) {
            throw new RuntimeException("used to much : " + used);
        }
//        long t1 = System.currentTimeMillis();
//        Storage byIdInLock = storageMapper.getByIdInLock(productId);
//        log.error("select time:" + (System.currentTimeMillis() - t1));
        int index = storageMapper.updateUsed(productId, used);
        return index > 0;
    }

    /**
     * 业务逻辑非常不严谨，主要是为了实践 seata 的 TCC 模式
     * @param businessActionContext
     * @param productId
     * @param used
     * @return
     */
    @Override
    public boolean prepareUpdateUseNum(BusinessActionContext businessActionContext, long productId, long used) {
        log.info("减少库存，第一阶段锁定库存，productId="+productId+"， used="+used);
        log.info("prepareUpdateUseNum inGlobalTransaction:{},xid:{},branchType:{}",RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());

        if (used > 10) {
            throw new RuntimeException("used to much : " + used);
        }
        int index = storageMapper.updateUsed(productId, used);
        return index > 0;
    }

    @Override
    public boolean commitUpdateUseNum(BusinessActionContext businessActionContext) {
        // TODO 缺少幂等
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        long used = Long.parseLong(businessActionContext.getActionContext("used").toString());
        log.info("减少库存，提交，productId="+productId+"， used="+used);
        return true;
    }

    @Override
    public boolean rollbackUpdateUseNum(BusinessActionContext businessActionContext) {
        // TODO 缺少幂等, 空回滚，悬挂
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        long used = Long.parseLong(businessActionContext.getActionContext("used").toString());
        log.info("减少库存，回滚，productId="+productId+"， used="+used);
        int index = storageMapper.updateUsed(productId, -used);
        return index > 0;
    }
}
