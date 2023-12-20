package com.zyc.learn_quartz.utils;

import org.junit.Test;
import org.quartz.CronExpression;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DFUtil {
    /**
     * 有网友指出，SimpleDateFormat 存在线程安全问题
     * 有三种方式可以解决这个问题
     * 1、用局部变量
     * 2、加锁
     * 3、用 ThreadLocal
     * 这里为了方便，就用局部变量的方式吧
     */
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * cron 带时区
     */
    @Test
    public void cronWithTimeZone() throws ParseException {
        // 创建一个CronExpression对象，指定的Cron表达式是每天的上午10点执行
        CronExpression cronExpr = new CronExpression("0 0 0 * * ?");

        // 设置时区为纽约（美国东部夏令时）
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        cronExpr.setTimeZone(timeZone);

        // 获取当前时间的下一次执行时间
        Date nextRun = cronExpr.getNextValidTimeAfter(new Date());

        // 打印下一次执行时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        System.out.println("Next run: " + dateFormat.format(nextRun));
    }
}
