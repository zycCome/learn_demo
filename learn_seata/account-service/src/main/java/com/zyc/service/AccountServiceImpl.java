package com.zyc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyc.mapper.AccountMapper;
import com.zyc.model.Account;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @author zyc66
 * @date 2024/11/27 15:41
 **/
@Service
@Slf4j
public class AccountServiceImpl implements AccountService{


    @Autowired
    private AccountMapper accountMapper;


    @Override
    public void updateMoney(Long userId,int money) {
        log.info("inGlobalTransaction:{},xid:{},branchType:{}", RootContext.inGlobalTransaction(),RootContext.getXID(),RootContext.getBranchType());

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);


        BigDecimal moneyBigDecimal = BigDecimal.valueOf(money);
        Account account = accountMapper.selectOne(queryWrapper);
        if(account == null) {
            throw new RuntimeException("用户不存在");
        }
        account.setMoney(account.getMoney().subtract(moneyBigDecimal));

        accountMapper.updateById(account);
    }

}
