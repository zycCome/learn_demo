package com.zyc.annotation;

import com.zyc.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuyc
 * @date 2021/09/03 23:02
 **/
@Component
public class UserService2 {

    @Autowired
    private User user;

    public UserService2(){
        System.out.println("userService construct");
    }

}
