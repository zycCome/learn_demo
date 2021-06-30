package com.zyc.learn_quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class SimpleExample {

    public void run() throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        Date runTime = evenMinuteDate(new Date());


        /**
         * 每次都是new一个Job对象
         */
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

        /**
         * trigger和job可以没有直接关联，在Scheduler处指定；但是也可以有关系
         */
//        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever())
                .build();

        sched.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + runTime);

        sched.start();

        try {
            Thread.sleep(65L * 1000L);
        } catch (Exception e) {
        }

        sched.shutdown(true);
    }

    public static void main(String[] args) throws Exception {

        SimpleExample example = new SimpleExample();
        example.run();

    }

}
