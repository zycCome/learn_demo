package com.zyc.config;


import com.zyc.service.ATiger;
import com.zyc.service.BTiger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired(required = false)
    private ATiger aTiger;


    @Autowired(required = false)
    private BTiger bTiger;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 服务启动完成后执行的逻辑
        System.out.println("✅ 应用启动完成，端口：" + event.getApplicationContext().getEnvironment().getProperty("server.port"));
        System.out.println("aTiger: "+aTiger);
        System.out.println("bTiger: "+bTiger);
    }
}

