package com.zyc.service;

import com.zyc.entity.User;

/**
 * @author zyc66
 */
public interface UserService {
    User checkUser(String username, String passwod);


    boolean postUser(String username, String passwod);

    void openTrace();

    void api1();
}
