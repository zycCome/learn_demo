package com.zyc.mybatis.mapper;

import com.zyc.mybatis.pojo.User;

import java.util.List;

/**
 * @author zhuyc
 * @date 2022/04/21 12:40
 **/
public interface UserMapper {

    List<User> list();

    List<User> list2();
}
