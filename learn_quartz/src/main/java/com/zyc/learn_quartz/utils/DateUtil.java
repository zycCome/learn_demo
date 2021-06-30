package com.zyc.learn_quartz.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * @author zhuyc
 * @date 2021/06/29 21:53
 **/
public class DateUtil {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime localDateTime) {
        return dateTimeFormatter.format(localDateTime);
    }



}
