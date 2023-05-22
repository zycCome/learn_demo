package com.zyc.liteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 描述:测试基类
 * @description:
 * @return:
 * @author: RicTeng
 * @time: 2020-01-15 17:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LiteflowExampleApplication.class)
public class LiteflowTest {

    @Before
    public void setUp() throws Exception {
        initData();
    }

    protected void initData() {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBase() {
        Assert.assertTrue(true);
    }

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    public  void testIf() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain6", "arg");
        System.out.println(response);
    }

    @Test
    public  void testIf2() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain6_1", "arg");
        System.out.println(response);
    }


    @Test
    public  void testSwitch() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain7", "arg");
        System.out.println(response);
    }


    @Test
    public  void testSwitch2() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain7_1", "arg");
        System.out.println(response);
    }


    /**
     * 测试不同流程中id一样的子变量是否会冲突？结论两个id为t2的变量互相不影响
     */
    @Test
    public  void testConflict() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain7", "arg");
        System.out.println(response);
        System.out.println("----------------------");
        LiteflowResponse response2 = flowExecutor.execute2Resp("chain6", "arg");
        System.out.println(response2);
        System.out.println("----------------------");
        LiteflowResponse response3 = flowExecutor.execute2Resp("chain7", "arg");
        System.out.println(response3);
    }

}

