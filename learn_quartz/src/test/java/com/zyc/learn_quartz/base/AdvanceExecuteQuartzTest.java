package com.zyc.learn_quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;


/**
 * 测试org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow=N，是否会提前触发.结论：会
 */
public class AdvanceExecuteQuartzTest {

    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();



            JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();


            // 0 1 2 3
            // 测试cron表达式
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("30 * * * * ? *")
                    )
                    .build();

            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startNow()
                    .forJob("job1", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("35 * * * * ? *")
                    )
                    .build();


            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(trigger2);
            scheduler.start();
            TimeUnit.SECONDS.sleep(300000);
            scheduler.shutdown();

        } catch (SchedulerException | InterruptedException se) {
            se.printStackTrace();
        }
    }
}
