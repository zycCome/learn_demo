package com.hanmc.example.modulardemo.persistent.domain;

import com.hanmc.example.modulardemo.common.ApiResponse;
import com.hanmc.example.modulardemo.open.OpenApiResponse;

import java.lang.reflect.Field;
// 没有export的类是无法使用的
//import com.hanmc.example.modulardemo.util.MathUtils ;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/25 6:01 PM
 * @Version 1.0.0
 **/
public class User {


    public ApiResponse test1() {
        return null;
    }


    public static void main(String[] args) throws IllegalAccessException {
        // export导出的类，反射中只能访问到public的字段，private的不可以被访问
        Field[] fields = ApiResponse.class.getDeclaredFields();
        ApiResponse apiResponse = new ApiResponse();
        for (Field field : fields) {
            System.out.println(field.getName());
            // 因为没有opens，所以反射会报错
            try {
                field.setAccessible(true);
                field.setInt(apiResponse,1);
            } catch (Exception e) {
                System.out.println("反射报错");
            }
        }

        System.out.println("--------");
        //
        Field[] fields2 = OpenApiResponse.class.getDeclaredFields();
        OpenApiResponse apiResponse2 = new OpenApiResponse();
        // opens了，反射不会报错
        for (Field field : fields2) {
            System.out.println(field.getName());
            try {
                field.setAccessible(true);
                field.setInt(apiResponse2,1);
            } catch (Exception e) {
                System.out.println("反射报错");
            }
        }
    }



}
