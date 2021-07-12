package com.zyc.learn_quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;


/**
 * quartz能否保证trigger更新后，之前acquire的trigger变更？
 */
public class QuartzTestCurrentUpdate {

    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(
                        CronScheduleBuilder.cronSchedule("40 * * * * ? *")
                    )
                    .build();


            scheduler.scheduleJob(job, trigger);
            scheduler.start();


            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("20 * * * * ? *")
                    )
                    .build();

            scheduler.rescheduleJob(trigger.getKey(), newTrigger);


            TimeUnit.SECONDS.sleep(300000);
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
