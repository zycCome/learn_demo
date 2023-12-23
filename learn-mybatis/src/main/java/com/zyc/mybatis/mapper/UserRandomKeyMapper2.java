package com.zyc.mybatis.mapper;


import com.zyc.mybatis.pojo.UserRandomKey2;

/**
* @author zilu
* @description 针对表【user_random_key】的数据库操作Mapper
* @createDate 2022-06-13 14:06:51
* @Entity com.zyc.mybatis.pojo.UserRandomKey
*/
public interface UserRandomKeyMapper2 {

    int deleteByPrimaryKey(Long id);

    int insert(UserRandomKey2 record);

    int insertSelective(UserRandomKey2 record);

    UserRandomKey2 selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRandomKey2 record);

    int updateByPrimaryKey(UserRandomKey2 record);

}
