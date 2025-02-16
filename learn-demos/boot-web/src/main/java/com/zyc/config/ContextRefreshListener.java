package com.zyc.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 当 ApplicationContext 初始化或刷新时触发
        System.out.println("🔄 Context 刷新完成：" +
            event.getApplicationContext().getDisplayName());
    }
}

