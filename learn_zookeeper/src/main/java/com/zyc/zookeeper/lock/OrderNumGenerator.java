package com.zyc.zookeeper.lock;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhuyc
 * @date 2022/04/21 10:51
 **/
public class OrderNumGenerator {

    private static long count = 0;

    /**
     * 使用日期加数值拼接成订单号
     */
    public String getOrderNumber() throws Exception {
        String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String number = new DecimalFormat("000000").format(count++);
        return date + number;
    }


}
