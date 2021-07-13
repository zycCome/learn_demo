package com.zyc.learn_quartz.base;

import com.zyc.learn_quartz.utils.DFUtil;
import org.quartz.*;

import java.util.Date;
import java.util.StringJoiner;

@PersistJobDataAfterExecution
public class StateJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        System.out.println(
                new StringJoiner(" | ")
                        .add(DFUtil.format(new Date()))
                        .add(String.valueOf(this.hashCode()))
                        .add(jobDataMap.getString("a"))
        );

        jobDataMap.put("a","b");
    }
}
