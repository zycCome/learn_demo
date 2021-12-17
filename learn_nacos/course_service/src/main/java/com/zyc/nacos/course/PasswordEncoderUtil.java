package com.zyc.nacos.course;

/**
 * @author zhuyc
 * @date 2021/11/25 9:48
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password encoder tool
 *
 * @author nacos
 */
public class PasswordEncoderUtil {

    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("nacos"));
        //常规反
        System.out.println(new BCryptPasswordEncoder().encode("xxx"));
    }
}

