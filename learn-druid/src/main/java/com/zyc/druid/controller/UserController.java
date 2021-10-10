package com.zyc.druid.controller;

import com.zyc.druid.pojo.po.User;
import com.zyc.druid.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhuyc
 * @date 2021/10/10 11:44
 **/

@RestController
public class UserController {

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "saveUser")
    public User saveUser() {
        System.out.println(dataSource);
        return userRepos.save(new User());
    }

    @RequestMapping(value = "/findByUserName")
    public List<User> findByUserName(String userName) {
        return userRepos.findByUserName(userName);
    }

    @RequestMapping(value = "findByUserNameLike")
    public List<User> findByUserNameLkie(String userName) {
        return userRepos.findByUserNameLike(userName);
    }

    @RequestMapping(value = "findByPage")
    public Page<User> findByPage(Integer userType) {
        return userRepos.findByUserType(userType, PageRequest.of(1, 5));
    }


}
