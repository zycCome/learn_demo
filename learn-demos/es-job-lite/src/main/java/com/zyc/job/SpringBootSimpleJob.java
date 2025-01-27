package com.zyc.job;

import java.time.LocalDateTime;
import java.util.List;

import com.zyc.entity.Foo;
import com.zyc.repository.FooRepository;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SpringBootSimpleJob implements SimpleJob {

    private final Logger logger = LoggerFactory.getLogger(SpringBootSimpleJob.class);

    @Autowired
    private FooRepository fooRepository;

    @Override
    public void execute(final ShardingContext shardingContext) {
        logger.info("Item: {} | Time: {} | Thread: {} | {}",
                shardingContext.getShardingItem(), LocalDateTime.now(), Thread.currentThread().getId(), "SIMPLE");
        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }

}