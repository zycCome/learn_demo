package com.zyc.boot.service.impl;

import com.zyc.boot.pojo.po.DevelopTaskProject;
import com.zyc.boot.mapper.DevelopTaskProjectMapper;
import com.zyc.boot.service.DevelopTaskProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 开发任务和项目关联关系表 服务实现类
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
@Service
@Slf4j
public class DevelopTaskProjectServiceImpl extends ServiceImpl<DevelopTaskProjectMapper, DevelopTaskProject> implements DevelopTaskProjectService {


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveOne(DevelopTaskProject developTaskProject, boolean throwE) {
        log.info("[saveOne] start");
        this.save(developTaskProject);
        if (throwE) {
            log.info("[saveOne] throw Exception");
            throw new RuntimeException("测试异常");
        }
        log.info("[saveOne] end");
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void saveOne1(DevelopTaskProject developTaskProject, boolean throwE) {
        log.info("[saveOne] start");
        this.save(developTaskProject);
        if (throwE) {
            log.info("[saveOne] throw Exception");
            throw new RuntimeException("测试异常");
        }
        log.info("[saveOne] end");
    }
}
