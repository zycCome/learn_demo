package com.zyc;

import org.flowable.engine.impl.db.DbSchemaCreate;
import org.junit.Test;

/**
 * 配置篇
 *
 * @author zhuyc
 * @date 2021/12/26 08:55
 **/
public class BPMN_Config {


    @Test
    public void createDb() {
        DbSchemaCreate.main(null);
    }

}
