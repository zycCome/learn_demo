package com.zyc.learn_demo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author zhuyc
 * @date 2021/06/02 07:07
 **/
public class JavaSPI {

    public static void main(String[] args) {
        /*
        需要跟进去看源码
         */
        ServiceLoader<IAnimal> serviceLoader = ServiceLoader.load(IAnimal.class);
//        serviceLoader.forEach(IAnimal::say);
        Iterator<IAnimal> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            IAnimal next = iterator.next();
            next.say();
        }
        System.out.println("end");
    }

}
