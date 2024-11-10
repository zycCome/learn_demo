package com.zyc.aop;


import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert() {
        String sql = "insert into `tbl_user`(username, age) values(?, ?)";
        String username = UUID.randomUUID().toString().substring(0, 5);
//        jdbcTemplate.queryForList("select sleep(5)");
        jdbcTemplate.update(sql, username, 19); // 增删改都来调用这个方法
    }


    public java.util.List<Map<String, Object>> selectAll() {
        String sql = "select * from tbl_user";
        return jdbcTemplate.queryForList(sql); // 增删改都来调用这个方法
    }

}

