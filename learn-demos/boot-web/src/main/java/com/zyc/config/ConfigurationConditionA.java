package com.zyc.config;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConfigurationConditionA implements ConfigurationCondition {

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        /**
         * 和 REGISTER_BEAN 的区别：REGISTER_BEAN 还是会解析配置类ConfigB，只是不会创建；PARSE_CONFIGURATION 直接不会把 ConfigB作为配置类，当然也就不会解析ConfigB了
         */
        return ConfigurationPhase.PARSE_CONFIGURATION;
    }

//    @Override
//    public ConfigurationPhase getConfigurationPhase() {
//        return ConfigurationPhase.PARSE_CONFIGURATION;
//    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return false;
    }
}

