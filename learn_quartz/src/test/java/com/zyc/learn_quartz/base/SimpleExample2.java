package com.zyc.learn_quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * 1. forJob 不用指定具体的jobDetail对象，只需要指定jobName和jobGroup
 * 2. jobDetail可以获取到trigger的信息
 * 3. jobDetail可以被多个trigger触发（复用）
 * 4. 但是一个触发器只能调度一个jobDetail。一对多
 * 5. JobDetail和trigger都可以不声明group和name，quartz会自动生成随机值
 * 6. SimpleScheduleBuilder的调度规则：根据 间隔+重复次数（至少一次）
 */
public class SimpleExample2 {

    public void run() throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        Date runTime = evenMinuteDate(new Date());

        /*
         * 每次都是new一个Job对象
         * 并且一个Job Class可以生成多个JobDetail，然后被多个Trigger触发；没有限制
         */
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();


        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5)
//                    .repeatForever()
                    .withRepeatCount(0)
                )
                .build();

        Trigger trigger2 = newTrigger()
                .withIdentity("trigger2", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(2)
//                        .repeatForever()
                )
                /*
                 * 直接绑定jobDetail，声明jobDetail的信息
                 * 这种写法有个前提：scheduler能够感知到这里的JobDetail（比如之前有trigger使用过这个jobDetial了）
                 */
                .forJob("job1", "group1")
                .build();

        scheduler.scheduleJob(job, trigger);
        scheduler.scheduleJob(trigger2);
        System.out.println(job.getKey() + " will run at: " + runTime);

        scheduler.start();

        try {
            Thread.sleep(65L * 1000L);
        } catch (Exception e) {
        }

        scheduler.shutdown(true);
    }

    public static void main(String[] args) throws Exception {

        SimpleExample2 example = new SimpleExample2();
        example.run();

    }

}
