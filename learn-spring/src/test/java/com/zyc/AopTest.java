package com.zyc;

import com.zyc.aop.*;
import com.zyc.transaction.ITxA;
import com.zyc.transaction.MyConfigOfTX;
import com.zyc.transaction.TxB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 切面测试
 *
 * @author zhuyc
 * @date 2021/09/07 08:02
 **/
public class AopTest {

    @Test
    public void test() {
        //创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);
        //获取容器中的对象
        DemoI demo = applicationContext.getBean(DemoI.class);
        //按Demo类型，获取不到bean
//        DemoI demo2=applicationContext.getBean(Demo.class);
//        System.out.println(demo2);

        demo.printHello();
// 转不了，因为有接口，所以是用jdk 动态代理实现的
//        Demo d1 = (Demo)demo;
//        d1.printHello2();

        //如果找到多个相同类型的组件，那么再将属性的名称作为组件的id，到IOC容器中进行查找
        BeanA beanA = applicationContext.getBean(BeanA.class);
        System.out.println(beanA.getRepeatedBeanB().getType());

        BeanC beanC = applicationContext.getBean(BeanC.class);
        System.out.println(beanC);
        applicationContext.close();

    }


    /**
     * 测试开启事务后 和 普通AOP之间的代理关系
     */
    @Test
    public void testTx() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);


        DemoI demo = applicationContext.getBean(DemoI.class);
        //按Demo类型，获取不到bean
//        DemoI demo2=applicationContext.getBean(Demo.class);
//        System.out.println(demo2);
        demo.printHello();

        BeanB beanB = applicationContext.getBean(BeanB.class);
        System.out.println(beanB.getClass());
        beanB.test1();


        UserService userService = applicationContext.getBean(UserService.class);
        userService.insertUser();

        applicationContext.close();


    }


    /**
     * 测试编程式事务
     */
    @Test
    public void testCodeTx() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);


        PlatformTransactionManager transactionManager = applicationContext.getBean(PlatformTransactionManager.class);
        /**
         * 定义事务
         */
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setReadOnly(false);
        //隔离级别,-1表示使用数据库默认级别
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //TODO something
            System.out.println(2);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }

        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);


        //开启事务保存数据
        boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    // TODO something
                    System.out.println(2222);
                } catch (Exception e) {
                    //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
                    status.setRollbackOnly();
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
        System.out.println(result);


        DemoI demo = applicationContext.getBean(DemoI.class);
        //按Demo类型，获取不到bean
//        DemoI demo2=applicationContext.getBean(Demo.class);
//        System.out.println(demo2);
        demo.printHello();
//
//        BeanB beanB = applicationContext.getBean(BeanB.class);
//        System.out.println(beanB.getClass());
//        beanB.test1();


        UserService userService = applicationContext.getBean(UserService.class);
        userService.insertUser();

        applicationContext.close();


    }


    /**
     * 测试事务代理bean
     */
    @Test
    public void testTxPackage() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfTX.class);

        ITxA a = applicationContext.getBean(ITxA.class);
        a.a();

        TxB b = applicationContext.getBean(TxB.class);
        b.b1();
        b.b2();

        applicationContext.close();


    }

}
