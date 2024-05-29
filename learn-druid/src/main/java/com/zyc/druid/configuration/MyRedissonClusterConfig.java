package com.zyc.druid.configuration;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author zilu
 * @Date 2024/5/29 09:36
 * @Version 1.0.0
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.redis.cluster.nodes")
public class MyRedissonClusterConfig {
    private static final String REDIS_SSH_URL = "redis://";
    @Value(value = "${spring.redis.cluster.nodes}")
    private String host;
    @Value(value = "${spring.redis.password}")
    private String password;

    /**
     * 所有对redisson的使用都是通过RedissonClient来操作的
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        log.info("redisson启动，加载集群");

        // 1. 创建配置
        Config config = new Config();
        // 一定要加redis://
        String[] hostArr = host.split(",");
        String[] newHostArr = new String[hostArr.length];
        for (int i = 0; i < hostArr.length; i++) {
            newHostArr[i] = REDIS_SSH_URL + hostArr[i];
        }

        config.useClusterServers().addNodeAddress(newHostArr);
//        config.useClusterServers().setPassword(password);

        // 2. 根据config创建出redissonClient实例
        return Redisson.create(config);
    }
}

