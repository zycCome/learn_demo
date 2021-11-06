package com.zyc;

import org.springframework.beans.factory.FactoryBean;

/**
 * User工厂类
 *
 * @author zhuyc
 * @date 2021/09/03 22:30
 **/
public class UserFactoryBean implements FactoryBean<User> {
    User user = new User();

    @Override
    public User getObject() throws Exception {
//        return new User();
        return user;
    }

    public UserFactoryBean() {
        System.out.println("UserFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
