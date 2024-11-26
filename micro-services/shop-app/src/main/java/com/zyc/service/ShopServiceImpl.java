package com.zyc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zyc66
 * @date 2024/11/25 11:11
 **/
@Slf4j
@Service
public class ShopServiceImpl implements ShopService{


    @Override
    public String business1(int age, String note) {
        String result = age + note;
        log.info("result:{}" ,result);
        return result;
    }

}
