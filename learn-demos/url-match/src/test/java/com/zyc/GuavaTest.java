package com.zyc;

import com.google.common.base.Throwables;
import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/06/12 22:33
 **/
public class GuavaTest {


    @Test
    public void throwableTest() {
        try {
            new GuavaTest().ex2();
        } catch (Exception e) {
            e.printStackTrace();


            System.out.println("------------------");

            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }

    public void ex2() {
        try {
            ex1();
        } catch (Exception e) {
            throw new RuntimeException("ex2222", e);
        }
    }

    public void ex1() {
        int b = 2 / 0;
        System.out.println(1);
    }


    public static void main(String[] args) {
        float sum = 0.0f;
        float c = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            float y = x - c;
            float t = sum + y;
            c = (t - sum) - y;
            sum = t;
            if (sum >= 16777216) {
                System.out.println(c);
            }
        }
        System.out.println("sum is " + sum);
    }

    @Test
    public void test2() {
        float res = 0.0f;
        float remain = 0.0f;
        for (int i = 0; i < 60000000; i++) {
            float cur = 1.0f;
//            if(remain >=2 || remain <= -2) {
//                System.out.println(remain);
//            }
            float needToAdd = cur + remain;
            float nextRes = res + needToAdd;
            remain = needToAdd - (nextRes - res);
            if(remain < 0) {
                System.out.println(remain);
            }
//            if (res >= 16777216) {
//                System.out.println(remain);
//            }
            res = nextRes;
        }
        System.out.println(res);
    }

}
