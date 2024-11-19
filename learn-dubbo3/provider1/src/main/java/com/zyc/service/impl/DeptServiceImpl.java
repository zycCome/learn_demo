package com.zyc.service.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.api.DeptService;
import org.apache.dubbo.samples.api.UserService;
import org.apache.dubbo.samples.po.Dept;
import org.apache.dubbo.samples.po.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zyc66
 * @date 2024/11/18 13:24
 **/
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {


    @Value("${tag}")
    private String tag;

    @Override
    public Dept getByDeptId(Integer deptId) {
       log.info("{} getByUserId :{}", tag,deptId);

        return new Dept(deptId, RandomUtil.randomString(10));
    }
}
