package com.zyc.learn_demo.spi;

import java.util.ServiceLoader;

public class SPIDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ServiceLoader<GreetingService> serviceLoader = ServiceLoader.load(GreetingService.class);
        for (GreetingService service : serviceLoader) {
            service.sayHello();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("SPI加载耗时：" + (endTime - startTime) + "ms");
    }

}
