package com.zyc.service;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author zyc66
 * @date 2024/11/28 19:52
 **/
@Component
public class Jobs {

    @XxlJob("demo-job1")
    public void demoJob1() throws Exception {
        XxlJobHelper.log("demo1.");
    }

}
