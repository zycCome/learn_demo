package com.zyc.mybatis.mapper;


import com.zyc.mybatis.pojo.UserRandomKey;

/**
* @author zilu
* @description 针对表【user_random_key】的数据库操作Mapper
* @createDate 2022-06-13 14:06:51
* @Entity com.zyc.mybatis.pojo.UserRandomKey
*/
public interface UserRandomKeyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRandomKey record);

    int insertSelective(UserRandomKey record);

    UserRandomKey selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRandomKey record);

    int updateByPrimaryKey(UserRandomKey record);

}
