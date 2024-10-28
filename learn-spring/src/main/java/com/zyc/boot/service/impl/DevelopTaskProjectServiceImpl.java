package com.zyc.boot.service.impl;

import com.zyc.boot.pojo.po.DevelopTaskProject;
import com.zyc.boot.mapper.DevelopTaskProjectMapper;
import com.zyc.boot.service.DevelopTaskProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyc.boot.service.DevelopTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private DevelopTaskService developTaskService;


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


    /**
     * 结论：
     *   @Scheduled 默认单线程
     *   @Async 默认8个线程
     */
    @Scheduled(initialDelay = 5000,fixedDelay = 5000)
    public void scheduleTest2() {
        log.info("scheduleTest2:"+Thread.currentThread().getName());
        asyncTest3();

    }


    /**
     * 测试fixDelay是否会重复
     */
    @Scheduled(initialDelay = 5000,fixedDelay = 1000)
    public void scheduleFixDelay() {
        log.info("scheduleFixDelay start:"+Thread.currentThread().getName());
        int a = 1;
        int b = 2;

        log.info("scheduleFixDelay end:"+Thread.currentThread().getName());


    }


    /**
     * 测试fixDelay是否会重复
     */
    @Scheduled(initialDelay = 5000,fixedRate = 1000)
    public void scheduleFixedRate() {
        log.info("scheduleFixedRate start:"+Thread.currentThread().getName());
        int a = 1;
        int b = 2;

        log.info("scheduleFixedRate end:"+Thread.currentThread().getName());


    }


    @Async
    public void asyncTest3() {
        log.info("asyncTest内部调用无效:"+Thread.currentThread().getName());
    }


    @Async
    @Override
    public void asyncTest2() {
        log.info("asyncTest外部类:"+Thread.currentThread().getName());
    }
}
