package com.zyc;


import com.zyc.config.OpenFeignConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 参考：https://juejin.cn/post/7138298273676132360
 * @author zyc66
 */
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = OpenFeignConfig.class)
@MapperScan("com.zyc.mapper")
@Slf4j
public class UserApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserApplication.class, args);

        log.info("main run end");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        exitApplication(run);
    }



    public static void exitApplication(ConfigurableApplicationContext context) {
        //获取退出码
        int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
        //退出码传递给jvm，安全退出程序
        System.exit(exitCode);
    }

}