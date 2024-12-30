package com.zyc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zyc66
 * @date 2024/12/28 08:43
 **/
//@Component
@Slf4j
public class AppListener implements CommandLineRunner, DisposableBean {

    @Override
    public void run(String... args) throws Exception {
        log.info("应用启动成功，预加载相关数据");
    }

    @Override
    public void destroy() throws Exception {
        for (int i = 0; i < 30; i++) {
            log.info("优雅停止测试：{}",i);
            Thread.sleep(1000);
        }
        log.info("应用正在关闭，清理相关数据");
    }

}
