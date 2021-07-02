package com.zyc.learn_quartz.utils.jobconfig;

import com.zyc.learn_quartz.utils.job.SpringJob1;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JobInit {

    /**
     * scheduler由spring来创建
     */
    @Autowired
    public Scheduler scheduler;

    @PostConstruct
    public void initjob() throws SchedulerException {
        System.out.println("=-=-=-"+scheduler.getMetaData().getThreadPoolSize());

        JobDetail detail = JobBuilder.newJob(SpringJob1.class)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();

        scheduler.scheduleJob(detail, trigger);
    }

//    @Bean
//    @QuartzDataSource
//    public DataSource qdataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUsername("");
//        dataSource.setPassword("");
//        dataSource.setUrl("");
//        return dataSource;
//    }

}
