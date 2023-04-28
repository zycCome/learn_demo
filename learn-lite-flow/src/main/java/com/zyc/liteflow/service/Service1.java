package com.zyc.liteflow.service;

import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description
 * @Author zilu
 * @Date 2023/4/7 2:25 PM
 * @Version 1.0.0
 **/
@Component
@DependsOn({"a","b","c"})
public class Service1 implements ApplicationRunner {

    @Resource
    private FlowExecutor flowExecutor;


    @PostConstruct
    public void testConfig(){
//        LiteflowResponse response = flowExecutor.execute2Resp("chain4", "arg");
//        for (CmpStep cmpStep : response.getExecuteStepQueue()) {
//            System.out.println(cmpStep);
//        }
//
//        System.out.println("testConfig");


//        testCodeBuild();
    }

    private void testCodeBuild() {
        LiteFlowChainELBuilder.createChain().setChainName("chain_code").setEL(
                "cmpData = '{\"name\":\"jack\",\"age\":27,\"birth\":\"1995-10-01\"}';" +
                        "cmpData2 = '{\"name\":\"jack\",\"age\":27,\"birth\":\"1995-10-01\"}';"+
                        "THEN( a.data(cmpData) ,WHEN(b, c, a.data(cmpData2)));"
        ).build();


        LiteflowResponse response = flowExecutor.execute2Resp("chain_code", "arg");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testCodeBuild();
    }
}
