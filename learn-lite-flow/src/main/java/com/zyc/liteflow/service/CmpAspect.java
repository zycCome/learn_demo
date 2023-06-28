package com.zyc.liteflow.service;

import com.yomahub.liteflow.aop.ICmpAroundAspect;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.slot.Slot;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/10 6:04 PM
 * @Version 1.0.0
 **/
@Component
public class CmpAspect implements ICmpAroundAspect {

    @Override
    public void beforeProcess(String nodeId, Slot slot) {
        DefaultContext context = slot.getContextBean(DefaultContext.class);
        //before business
    }

    @Override
    public void afterProcess(String nodeId, Slot slot) {
        DefaultContext context = slot.getContextBean(DefaultContext.class);
        //after business
    }
}

