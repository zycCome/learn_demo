package com.zyc.config;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConfigurationConditionB implements ConfigurationCondition {

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

//    @Override
//    public ConfigurationPhase getConfigurationPhase() {
//        return ConfigurationPhase.PARSE_CONFIGURATION;
//    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 打印这行，说明ConfigB被扫描了
        System.out.println("ConfigB had parsed ");
        return true;
    }
}

