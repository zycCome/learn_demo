package com.zyc.boot.service;

import com.zyc.boot.pojo.po.DevelopTaskProject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 开发任务和项目关联关系表 服务类
 * </p>
 *
 * @author hejx
 * @since 2021-09-30
 */
public interface DevelopTaskProjectService extends IService<DevelopTaskProject> {

    void saveOne(DevelopTaskProject developTaskProject,boolean throwE);

    void saveOne1(DevelopTaskProject developTaskProject, boolean throwE);

}
