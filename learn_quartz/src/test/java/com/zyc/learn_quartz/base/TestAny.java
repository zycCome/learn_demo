package com.zyc.learn_quartz.base;

import org.junit.jupiter.api.Test;
import org.quartz.CronExpression;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestAny {

    @Test
    public void testCron() throws ParseException {

        /**
         * 测试cron表达式是否正确
         * 通过，不一定正确
         * 不通过，一定不正确
         */
//        CronExpression.validateExpression("0 0 0 L-3 * ? 2020");
//        CronExpression.validateExpression("0 0 0 1-3 * ? 2020");
        CronExpression.validateExpression("0 0 10am 1,15 * ?");
    }


    @Test
    public void test() {
        LocalDateTime dateTime = LocalDateTime.now().plus(30, ChronoUnit.DAYS);
        System.out.println(StringUtils.capitalize("aaa"));
        System.out.println(StringUtils.uncapitalize("Hello"));
    }
}
