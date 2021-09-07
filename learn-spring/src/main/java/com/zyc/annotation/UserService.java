package com.zyc.annotation;

import com.zyc.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuyc
 * @date 2021/09/03 23:02
 **/
public class UserService {

    @Autowired
    private User user;

    public UserService(User user,User user2){
        System.out.println("userService construct");
    }

    public UserService() {
        System.out.println("userService no args construct");
    }
}
