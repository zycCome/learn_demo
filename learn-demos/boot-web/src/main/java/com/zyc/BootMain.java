package com.zyc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
//@EnableConfigurationProperties(value = {ConfigProperties.class})
@ConfigurationPropertiesScan
public class BootMain {
    public static void main(String[] args) {
        SpringApplication.run(BootMain.class, args);
    }


}