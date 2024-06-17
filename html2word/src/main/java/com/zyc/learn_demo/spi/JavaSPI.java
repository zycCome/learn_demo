package com.zyc.learn_demo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 为什么移动到这里？因为在主项目下面读取不到resources文件，甚至target里面都没有这个文件。可能时项目类型是pom导致的
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

        Iterator<IAnimal> iterator2 = serviceLoader.iterator();
        while (iterator2.hasNext()) {
            IAnimal next = iterator2.next();
            next.say();
        }
        System.out.println("end");
    }

}
