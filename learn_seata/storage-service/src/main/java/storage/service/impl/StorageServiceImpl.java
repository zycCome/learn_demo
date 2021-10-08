package storage.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storage.mapper.StorageMapper;
import storage.service.StorageService;

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
//        int a = 100/0;
        if (used > 100) {
            throw new RuntimeException("used to much : " + used);
        }
        int index = storageMapper.updateUsed(productId, used);
        return index > 0;
    }
}
