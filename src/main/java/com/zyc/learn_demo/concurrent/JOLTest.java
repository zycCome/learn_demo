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
    }

    @Test
    public void testBiasedLockingStartupDelay() {
        //注释下面的代码会发现：没有使用偏向锁
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

}
//0101001000011011011111101000101

//<dependency>
//<groupId>org.openjdk.jol</groupId>
//<artifactId>jol-core</artifactId>
//<version>0.8</version>
//</dependency>
