package com.javacode2018.lesson001.demo25.test7;

/**
 * @author zyc66
 * @date 2023/04/04 21:05
 **/
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MyConfigurationCondition1 implements ConfigurationCondition {
    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN; //@1
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取spring容器
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //判断容器中是否存在Service类型的bean
        boolean existsService = !beanFactory.getBeansOfType(Service.class).isEmpty();
        return existsService;
    }
}
