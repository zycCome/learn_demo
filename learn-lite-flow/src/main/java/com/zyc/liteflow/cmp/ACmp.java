package com.zyc.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 2:19 PM
 * @Version 1.0.0
 **/
@Component("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        String cmpData = this.getCmpData(String.class);

        System.out.println("a process");
    }



}
