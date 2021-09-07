package com.zyc.annotation;

import com.zyc.User;
import org.springframework.beans.factory.BeanFactory;
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

    @Autowired
    private BeanFactory beanFactory;

    public UserService2(User user2){
        System.out.println(user2);
        System.out.println("userService2 construct");
        System.out.println(user);
    }

}
