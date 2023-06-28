package com.zyc.liteflow.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 3:02 PM
 * @Version 1.0.0
 **/
@LiteflowComponent("aSwitch")
public class ASwitchNode extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        System.out.println("Acomp executed!");
        return "t3";
    }

}
