package com.zyc.learn_quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;


//import static org.quartz.JobBuilder.*;
//import static org.quartz.TriggerBuilder.*;
//import static org.quartz.SimpleScheduleBuilder.*;
public class _06_QuartzTest_Data {

    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();


            JobDetail job = JobBuilder.newJob(HelloDataJob.class)
                    .usingJobData("detail-key-1","detail-value-1")
                    .usingJobData("hehe","a")
                    .withIdentity("job1", "group1")
                    .build();


            // 0 1 2 3
            /*
             * JobDetail和Trigger都可以设置obDataMap
             * 并且在Job初始化时，两者会合并（trigger中的优先级别高）。
             * 然后将合并后的值，通过key判断job中是否存在对应的set方法，有则赋值（自动注入）
             */
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .usingJobData("trigger-key-1","trigger-value-1")
                    .usingJobData("hehe","b")
                    .startNow()
                    .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * * * * ? *")
                    )
                    .build();


            scheduler.scheduleJob(job, trigger);

            TimeUnit.SECONDS.sleep(1);
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
