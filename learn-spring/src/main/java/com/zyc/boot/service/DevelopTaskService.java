package com.zyc.boot.service;

import com.zyc.boot.pojo.po.DevelopTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 开发任务 服务类
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
public interface DevelopTaskService extends IService<DevelopTask> {

    void insert2Table(boolean throwE);

    void insert2Table1(boolean throwE);

    void insert2Table2(boolean throwE);

    void notPublicTransaction();

}
