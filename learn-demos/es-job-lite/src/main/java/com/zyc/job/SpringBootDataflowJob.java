package com.zyc.job;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import com.zyc.entity.Foo;
import com.zyc.repository.FooRepository;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class SpringBootDataflowJob implements DataflowJob<Foo> {

    private final Logger logger = LoggerFactory.getLogger(SpringBootDataflowJob.class);

    @Resource
    private FooRepository fooRepository;

    @Override
    public List<Foo> fetchData(final ShardingContext shardingContext) {
        logger.info("Item: {} | Time: {} | Thread: {} | {}",
                shardingContext.getShardingItem(), LocalDateTime.now(), Thread.currentThread().getId(), "DATAFLOW FETCH");
        return fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
    }

    @Override
    public void processData(final ShardingContext shardingContext, final List<Foo> data) {
        logger.info("Item: {} | Time: {} | Thread: {} | {}",
                shardingContext.getShardingItem(), LocalDateTime.now(), Thread.currentThread().getId(), "DATAFLOW PROCESS");
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}

