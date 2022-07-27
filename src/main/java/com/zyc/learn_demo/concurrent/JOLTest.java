package com.zyc.learn_demo.concurrent;


import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import static java.lang.System.out;

/**
 * 对象头信息打印
 *
 * @author zhuyc
 * @date 2021/7/15 9:50
 */
public class JOLTest {

    public static void main(String[] args) {

        A a = new A();
        out.println(VM.current().details());
        out.println("---------------------");
        out.println(ClassLayout.parseInstance(a).toPrintable());

    }



    static class A extends Parent{
        //要看一看，写这个boolean和不写这个boolean 输出的对象头信息有什么区别，下面的两坨黑乎乎的就是输出
        boolean flag = false;
    }

    static class Parent {
        String name = "name";
    }

    @Test
    public void printHashCode() {
        A a = new A();
        out.println("before hash");
        out.println(VM.current().details());
        out.println(ClassLayout.parseInstance(a).toPrintable());
        out.println("计算hashcode:::::: " + Integer.toHexString(a.hashCode()));
        out.println("after hash");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }


    /**
     * 测试hashcode导致无法使用偏向锁
     */
    @Test
    public void afterHashCode() {
        A a = new A();
        out.println("before hash");
        out.println("计算hashcode:::::: " + Integer.toHexString(a.hashCode()));

        out.println("after hash");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            out.println("locking");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }

    /**
     * 因为Object.wait机制用到了monitor对象，因此测试是否会直接使用重量级锁。
     * 猜测：应该会。wait本身就要休眠了，自旋没有意义了
     * 结论：确实会直接用重量级锁
     */
    @org.junit.Test
    public void testWait() throws InterruptedException {
        // 延迟偏向
        Thread.sleep(6000);
        Object a = new Object();

        out.println("start-----");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        new Thread() {
            @Override
            public void run() {
                synchronized (a){
                    out.println("first locking start---:"+ClassLayout.parseInstance(a).toPrintable());
                    try {
                        a.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    out.println("first locking end---:"+ClassLayout.parseInstance(a).toPrintable());
                }
            }
        }.start();

        Thread.sleep(1000);

        new Thread() {
            @Override
            public void run() {
                synchronized (a){
                    out.println("second locking start---:"+ClassLayout.parseInstance(a).toPrintable());
                    a.notify();
                    out.println("second locking end---:"+ClassLayout.parseInstance(a).toPrintable());
                }
            }
        }.start();

        Thread.sleep(10000);
        out.println("end:"+ClassLayout.parseInstance(a).toPrintable());
    }

    @Test
    public void testBiasedLockingStartupDelay() {
//        //注释下面的代码会发现：没有使用偏向锁
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        A a = new A();
        out.println("before lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            out.println("locking");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }

    /**
     * 目的：测试锁是否会降级。轻量级锁降到偏向锁状态
     * 结论：偏向锁升级之后，无锁状态不会再变回偏向锁（排除偏向锁撤销、重偏向）
     */
    @org.junit.Test
    public void  testLockDemotion() throws InterruptedException {

        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object lightObject = new Object();
        Object biasedLockObject = new Object();
        System.out.println("---------------------------------------加锁前---------------------------------------");
        System.out.println("偏向锁：" + ClassLayout.parseInstance(biasedLockObject).toPrintable() + "\n轻量级锁：" + ClassLayout.parseInstance(lightObject).toPrintable());
        System.out.println("---------------------------------------加锁后---------------------------------------");

        synchronized (lightObject) {
            System.out.println("轻量级锁第一次：" + ClassLayout.parseInstance(lightObject).toPrintable());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
//        System.out.println("---------------------------------------释放锁---------------------------------------");
//        System.out.println("偏向锁：" + ClassLayout.parseInstance(biasedLockObject).toPrintable() + "\n轻量级锁：" + ClassLayout.parseInstance(lightObject).toPrintable());

        for (int i=0;i<10;i++) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
//                    synchronized (biasedLockObject) {
//                        System.out.println("竞争偏向锁：" + ClassLayout.parseInstance(biasedLockObject).toPrintable());
//                    }
//
//                    System.out.println("竞争偏向锁释放：" + ClassLayout.parseInstance(biasedLockObject).toPrintable());
                    synchronized (lightObject) {
                        System.out.println("竞争轻量级锁：" + ClassLayout.parseInstance(lightObject).toPrintable());
                    }

//              System.out.println("竞争偏向锁释放：" + ClassLayout.parseInstance(biasedLockObject).toPrintable());
                }

            });
            thread.start();
        }

        Thread.sleep(5000);
        System.out.println("轻量级锁释放后：" + ClassLayout.parseInstance(lightObject).toPrintable());


        synchronized (lightObject) {
            System.out.println("轻量级锁最后一次：" + ClassLayout.parseInstance(lightObject).toPrintable());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }

    }


    @Test
    public void testString() {
        String a = "";
        String b = "a";
        String c = "acasdasdasdasdasdasdas";
        char[] d = {'a','c','a','d','e'};
        out.println(ClassLayout.parseInstance(a).toPrintable());
        out.println(ClassLayout.parseInstance(b).toPrintable());
        out.println(ClassLayout.parseInstance(c).toPrintable());
        out.println(ClassLayout.parseInstance(d).toPrintable());

    }

}
//0101001000011011011111101000101

//<dependency>
//<groupId>org.openjdk.jol</groupId>
//<artifactId>jol-core</artifactId>
//<version>0.8</version>
//</dependency>
