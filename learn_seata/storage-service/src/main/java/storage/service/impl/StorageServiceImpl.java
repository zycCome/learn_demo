package storage.service.impl;


import io.seata.core.context.RootContext;
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
}
