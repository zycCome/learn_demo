/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zyc.demo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.zyc.demo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        normal(context);
//        generic(context);

    }

    private static void generic(ClassPathXmlApplicationContext context) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(new ApplicationConfig("test"));
        reference.setRegistry(new RegistryConfig("zookeeper://zhuyc.top:12181"));
        reference.setInterface("com.zyc.demo.api.DemoService");
        // 声明为泛化接口
        reference.setGeneric(true);

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        while (true) {
            int i = 0;
            try {
                Thread.sleep(1000);
                RpcContext.getContext().setAttachment("author", "zyc_"+i++);
                // 基本类型以及Date,List,Map等不需要转换，直接调用
                Object result = genericService.$invoke("sayHello", null,
                        new Object[] { "world generic" });
                System.out.println(result);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    private static void normal(ClassPathXmlApplicationContext context) {
        // get remote service proxy
        DemoService demoService = (DemoService) context.getBean("demoService");

        while (true) {
            try {
                Thread.sleep(1000);
                // call remote method
                String hello = demoService.sayHello("world");
                // get result
                System.out.println(hello);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
