package com.zyc.es.mapper;

import com.zyc.es.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuyc
 * @date 2022/06/25 16:25
 **/
@Repository
@Mapper
public interface GoodsMapper {
    /**
     * 查询所有
     */
    List<Goods> findAll();
}
