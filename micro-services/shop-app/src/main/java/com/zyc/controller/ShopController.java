package com.zyc.controller;

import com.zyc.entity.Order;
import com.zyc.entity.Shop;
import com.zyc.mapper.ShopMapper;
import com.zyc.result.CommonResult;
import com.zyc.result.ResultUtils;
import com.zyc.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyc66
 * @date 2024/11/24 14:45
 **/
@RestController
@RequestMapping("/shop")
@Slf4j
public class ShopController {


    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopService shopService;


    @GetMapping("/list")
    public CommonResult<List<Shop>> list() {
        log.info("list");
        List<Shop> shopList = new ArrayList<>();
        Shop shop = new Shop();
        shop.setId(879);
        shop.setShopName("百货商店");
        shopList.add(shop);
        return ResultUtils.success(shopList);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Shop> get(@PathVariable Long id) {
        shopService.business1(2222,"注意事项：1,2,3......");
        return ResultUtils.success(shopMapper.selectByPrimaryKey(1));
    }

}
