package com.zyc.mybatis;

import ch.qos.logback.classic.Level;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhuyc
 * @date 2022/04/21 12:39
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.zyc.mybatis.mapper")
public class MybatisApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取一个名为 "com.foo" 的 logger 对象，并且转换为 ch.qos.logback.classic.Logger logger，
        // 这样我们能为它设置级别
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");
//        logger.setLevel(Level.INFO);

        // 获取一个名为 "com.boo.Bar" 的 logger 对象，没有设置级别，根据继承关系，继承"com.foo"的 logger 的级别 INFO
        Logger barLogger = LoggerFactory.getLogger("com.foo.Bar");

        // 可以执行，因为 WARN >= INFO
        logger.debug("info level.");

//        // 根据级别继承关系，可以执行，因为 INFO >= INFO.
        barLogger.info("Located nearest gas station.");
//
//        // 根据级别继承关系，不能执行，因为 DEBUG < INFO.
//        barLogger.debug("Exiting gas station search");
    }


}
