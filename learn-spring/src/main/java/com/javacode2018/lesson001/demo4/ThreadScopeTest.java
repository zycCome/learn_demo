package com.javacode2018.lesson001.demo4;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.TimeUnit;
/**
 * 公众号：路人甲Java，工作10年的前阿里P7分享Java、算法、数据库方面的技术干货！坚信用技术改变命运，让家人过上更体面的生活!
 * <p>
 * 自定义scope
 */
public class ThreadScopeTest {
    public static void main(String[] args) throws InterruptedException {
        String beanXml = "classpath:demo4/beans-thread.xml";
        //手动创建容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(){
            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                //向容器中注册自定义的scope
                beanFactory.registerScope(ThreadScope.THREAD_SCOPE, new ThreadScope());//@1
                super.postProcessBeanFactory(beanFactory);
            }
        };
        //设置配置文件位置
        context.setConfigLocation(beanXml);
        //启动容器
        context.refresh();
        BeanScopeModelService beanScopeModelService = (BeanScopeModelService)context.getBean("beanScopeModelService");

        //使用容器获取bean
        for (int i = 0; i < 2; i++) {//@2
            new Thread(() -> {
                System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));
                System.out.println(Thread.currentThread() + "," + context.getBean("threadBean"));

                beanScopeModelService.printBeanScopeModel();

            }).start();
            TimeUnit.SECONDS.sleep(1);
        }

        beanScopeModelService.printBeanScopeModel();

    }
}
