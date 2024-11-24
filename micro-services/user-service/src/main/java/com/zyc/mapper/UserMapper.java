package com.zyc.mapper;

import com.zyc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zyc66
 */
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
