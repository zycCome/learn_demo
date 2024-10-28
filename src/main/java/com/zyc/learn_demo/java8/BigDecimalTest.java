package com.zyc.learn_demo.java8;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zyc66
 * @date 2024/10/15 12:01
 **/
public class BigDecimalTest {

    @Test
    public void testDoubleAndFloat() {
        float f1 = 2.0f - 1.9f;
        double d1 = 2.0 - 1.9;
        System.out.println("f1: "+f1);
        System.out.println("d1: "+d1);


        double value = 0.1;
        BigDecimal bd = new BigDecimal(value);
        // 输出为 0.1000000000000000055511151231257827021181583404541015625，精度丢失，不是我们所希望的
        System.out.println(bd);


        System.out.println(BigDecimal.valueOf(value));
    }

    @Test
    public void testSetScale() {
        BigDecimal b1 = new BigDecimal("1.0");
        // BigDecimal是不可变的，所以也是线程安全的
        BigDecimal b2 = b1.setScale(4);
        System.out.println(b1);
        System.out.println(b2);
    }


    /**
     * 测试相除时，精度是用除数还是被除数
     */
    @Test
    public void testDivide() {
        BigDecimal a = new BigDecimal("10.00");
        a = a.setScale(2);

        BigDecimal b = new BigDecimal("4.00");
        b = b.setScale(4);

        System.out.println(a);
        System.out.println(b);

        //刚好整除的情况下，不会有多余的小数部分0
        System.out.println(a.divide(b));
        System.out.println(a.divide(b,3, RoundingMode.HALF_UP));
    }



}
