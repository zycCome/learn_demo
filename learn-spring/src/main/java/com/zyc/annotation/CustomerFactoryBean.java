package com.zyc.annotation;


import com.zyc.annotation.UserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


/**
 * @author zhuyc
 * @date 2021/09/03 23:03
 **/
@Component
public class CustomerFactoryBean implements FactoryBean<UserService> {
    @Override
    public UserService getObject() throws Exception {
        return new UserService();
    }

    public CustomerFactoryBean() {
        System.out.println("CustomerFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }
}
