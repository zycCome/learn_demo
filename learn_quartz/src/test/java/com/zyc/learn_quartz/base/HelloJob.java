package com.zyc.learn_quartz.base;

import com.zyc.learn_quartz.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;


public class HelloJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取trigger的信息
        System.out.println(this.hashCode()+" Hello World! - " + context.getTrigger().getKey().getName() +" - " +DateUtil.format(LocalDateTime.now()));
    }
}
