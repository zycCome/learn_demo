package com.zyc.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 2:20 PM
 * @Version 1.0.0
 **/
@Component("c")
public class CCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        Object requestData = this.getRequestData();
        System.out.println(requestData);
        DefaultContext contextBean = this.getContextBean(DefaultContext.class);
        System.out.println("c process");
    }
}