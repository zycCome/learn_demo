package com.zyc.boot.service.impl;

import com.zyc.boot.pojo.po.DevelopTask;
import com.zyc.boot.mapper.DevelopTaskMapper;
import com.zyc.boot.pojo.po.DevelopTaskProject;
import com.zyc.boot.service.DevelopTaskProjectService;
import com.zyc.boot.service.DevelopTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * <p>
 * 开发任务 服务实现类
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
@Service
@Slf4j
public class DevelopTaskServiceImpl extends ServiceImpl<DevelopTaskMapper, DevelopTask> implements DevelopTaskService {


    @Autowired
    private DevelopTaskProjectService developTaskProjectService;
//    @Autowired
//    private DevelopTaskServiceImpl developTaskServiceImpl;


    /**
     * 插入到两张表里面
     *
     * @param throwE
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert2Table(boolean throwE) {
        log.info("[insert2Table] start");
        DevelopTask developTask = new DevelopTask();
        long timeMillis = System.currentTimeMillis();
        developTask.setAppKey(timeMillis + "");
        developTask.setAppSecret(timeMillis + "");
        developTask.setName("测试");
        developTask.setProjectNumber(100);
        this.save(developTask);

        DevelopTaskProject developTaskProject = new DevelopTaskProject();
        developTaskProject.setProjectId(timeMillis);
        Random random = new Random();
        developTaskProject.setTaskId(random.nextInt(100000000));
        try {
            developTaskProjectService.saveOne(developTaskProject, false);
        } catch (Exception e) {
            log.error("[insert2Table] " + e.getMessage());
        }
        if (throwE) {
            throw new RuntimeException("测试回滚");
        }
        log.info("[insert2Table] end");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert2Table1(boolean throwE) {
        log.info("[insert2Table1] start");

        DevelopTask developTask = new DevelopTask();
        long timeMillis = System.currentTimeMillis();
        developTask.setAppKey(timeMillis + "");
        developTask.setAppSecret(timeMillis + "");
        developTask.setName("测试");
        developTask.setProjectNumber(99);
        this.save(developTask);

        DevelopTaskProject developTaskProject = new DevelopTaskProject();
        developTaskProject.setProjectId(timeMillis);
        Random random = new Random();
        developTaskProject.setTaskId(random.nextInt(100000000));
        try {
            developTaskProjectService.saveOne(developTaskProject, throwE);
        } catch (Exception e) {
            log.error("[insert2Table1] catch exception : " + e.getMessage());
        }
        log.info("[insert2Table1] end");
    }

    /**
     * 内部是嵌套异常
     *
     * @param throwE
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert2Table2(boolean throwE) {
        log.info("[insert2Table1] start");

        DevelopTask developTask = new DevelopTask();
        long timeMillis = System.currentTimeMillis();
        developTask.setAppKey(timeMillis + "");
        developTask.setAppSecret(timeMillis + "");
        developTask.setName("测试");
        developTask.setProjectNumber(99);
        this.save(developTask);

        DevelopTaskProject developTaskProject = new DevelopTaskProject();
        developTaskProject.setProjectId(timeMillis);
        Random random = new Random();
        developTaskProject.setTaskId(random.nextInt(100000000));
        try {
            developTaskProjectService.saveOne1(developTaskProject, throwE);
        } catch (Exception e) {
            log.error("[insert2Table1] catch exception : " + e.getMessage());
        }
        log.info("[insert2Table1] end");
    }

    @Override
    public void notPublicTransaction() {
//        developTaskServiceImpl.notPublicSave();
        this.notPublicSave();
    }


    /**
     * 测试非public方法 事务是否生效
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void notPublicSave() {
        log.info("[insert2Table] start");
        DevelopTask developTask = new DevelopTask();
        long timeMillis = System.currentTimeMillis();
        developTask.setAppKey(timeMillis + "");
        developTask.setAppSecret(timeMillis + "");
        developTask.setName("测试");
        developTask.setProjectNumber(100);
        this.save(developTask);

        if (1 == 1) {
            throw new RuntimeException("测试回滚");
        }
        DevelopTaskProject developTaskProject = new DevelopTaskProject();
        developTaskProject.setProjectId(timeMillis);
        Random random = new Random();
        developTaskProject.setTaskId(random.nextInt(100000000));
        try {
            developTaskProjectService.saveOne(developTaskProject, false);
        } catch (Exception e) {
            log.error("[insert2Table] " + e.getMessage());
        }

        log.info("[insert2Table] end");
    }

    @Scheduled(initialDelay = 5000,fixedDelay = 5000)
    public void scheduleTest() throws InterruptedException {
        log.info("scheduleTest:" + Thread.currentThread().getName());
        developTaskProjectService.asyncTest2();
        Thread.sleep(2000);
    }

}
