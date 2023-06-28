package com.zyc.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 2:20 PM
 * @Version 1.0.0
 **/
@Component("b")
public class BCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("b process");
    }
}
