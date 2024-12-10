package com.zyc.learn_quartz.base;


import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 集群测试1
 *
 * @author zhuyc
 * @date 2021/07/05 22:51
 **/
public class TestQuartzCluster {

    public static boolean flag = false;

    private static Scheduler scheduler;

    public static void main(String[] args) throws Exception {
        try {
            flag = true;
            assginNewJob();
        } catch (ObjectAlreadyExistsException e) {
            System.err.println("发现任务已经在数据库存在了，直接从数据库里运行:"+ e.getMessage());
            // TODO Auto-generated catch block
            resumeJobFromDatabase();
        }
    }

    private static void resumeJobFromDatabase() throws Exception {
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        System.out.println("当前调度器的id是："+scheduler.getSchedulerInstanceId());
        scheduler.start();
        // 等待200秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(2000000);
        scheduler.shutdown(true);
    }

    private static void assginNewJob() throws SchedulerException, InterruptedException {
        // 创建调度器
        scheduler = new StdSchedulerFactory("quartz-cluster.properties").getScheduler();
        // 定义一个触发器
//        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") // 定义名称和所属的租
//                .startNow()
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(15) // 每隔15秒执行一次
//                        .withRepeatCount(10)) // 总共执行11次(第一次执行不基数)
//                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("20 * * * * ? *")
                )
                .build();

        // 定义一个JobDetail
        JobDetail job = newJob(MailJob.class) // 指定干活的类MailJob
                .withIdentity("mailjob1", "mailgroup") // 定义任务名称和分组
                .usingJobData("email", "admin@10086.com") // 定义属性
                .build();

        // 调度加入这个job
        scheduler.scheduleJob(job, trigger);
        System.out.println("当前调度器的id是："+scheduler.getSchedulerInstanceId());

        // 启动
        scheduler.start();

        // 等待20秒，让前面的任务都执行完了之后，再关闭调度器
        Thread.sleep(2000000);
        scheduler.shutdown(true);
    }
}

