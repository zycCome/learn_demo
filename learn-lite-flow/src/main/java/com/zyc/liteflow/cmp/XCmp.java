package com.zyc.liteflow.cmp;

import com.yomahub.liteflow.core.NodeIfComponent;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/17 3:29 PM
 * @Version 1.0.0
 **/
@Component("x")
public class XCmp extends NodeIfComponent {

    @Override
    public boolean processIf() throws Exception {
        return true;
    }
}
