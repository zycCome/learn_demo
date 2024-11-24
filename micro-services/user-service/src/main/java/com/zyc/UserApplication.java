package com.zyc;


import com.zyc.config.OpenFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 参考：https://juejin.cn/post/7138298273676132360
 * @author zyc66
 */
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = OpenFeignConfig.class)
@MapperScan
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}