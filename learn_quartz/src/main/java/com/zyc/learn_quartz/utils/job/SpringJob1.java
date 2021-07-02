package com.zyc.learn_quartz.utils.job;


import com.zyc.learn_quartz.utils.DateUtil;
import com.zyc.learn_quartz.utils.service.HelloSpringService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class SpringJob1 extends QuartzJobBean {

    @Autowired
    private HelloSpringService helloSpringService;

//    public void setHelloSpringService(HelloSpringService helloSpringService) {
//        this.helloSpringService = helloSpringService;
//    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String schedulerInstanceId = "default";
        try {
            schedulerInstanceId = context.getScheduler().getMetaData().getSchedulerInstanceId();
        } catch (SchedulerException e) {
        }
        StringJoiner outStr = new StringJoiner(" | ")
                .add("SpringJob1.executeInternal")
                .add(DateUtil.format(LocalDateTime.now()))
                .add(context.getJobDetail().getKey().getName())
                .add(context.getJobDetail().getJobDataMap().getString("key0"))
                .add(schedulerInstanceId)
                .add(helloSpringService.helloSpring());
        System.out.println(outStr);
    }
}
