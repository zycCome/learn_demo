package com.zyc.liteflow.service;

import com.yomahub.liteflow.script.annotation.ScriptBean;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 4:15 PM
 * @Version 1.0.0
 **/
@Component
@ScriptBean("demo")
public class DemoBean1 {


    public String getDemoStr1(){
        return "hello";
    }
}
