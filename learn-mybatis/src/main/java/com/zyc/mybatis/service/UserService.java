package com.zyc.mybatis.service;

import com.zyc.mybatis.mapper.UserMapper;
import com.zyc.mybatis.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zyc66
 * @date 2024/11/08 16:24
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void cacheInTransactionTest() {
        List<User> list = userMapper.list();
        // 查询结果为空也会缓存空集合
        System.out.println(list);
        System.out.println("------------");
        List<User> list2 = userMapper.list();
        System.out.println(list);
        // 肯定不一样，因为已经是两个session了
        System.out.println(list2 == list);


        List<User> users2 = userMapper.list2();
        System.out.println(users2);
    }


}
