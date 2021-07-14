package com.zyc.learn_demo.concurrent;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Unsafe类使用
 *
 * @author zhuyc
 * @date 2021/7/13 18:12
 */
public class UnsafeTest {


    /**
     * 获取Unsafe实例
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public Unsafe getInstance() {
        //直接调用该方法会抛出SecurityException，因为该类不希望让用户自己用
//        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            //因为是静态方法，所以不用传obj对象
            Unsafe unsafe = null;
            unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void instanceClass() throws InstantiationException {
        User u1 = new User();
        System.out.println(u1.age);//输出10

        Unsafe unsafe = getInstance();
        User u2 = (User) unsafe.allocateInstance(User.class);
        System.out.println(u2.age);//输出0


    }

    static class User {
        int age;

        public User() {
            this.age = 10;
        }
    }

    /**
     * 测试修改对象的字段
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void updateFiledValue() throws NoSuchFieldException {
        Unsafe unsafe = getInstance();
        User u1 = new User();

        Field ageField = User.class.getDeclaredField("age");
        //这里需要先获取age字段的偏移量
        unsafe.putInt(u1, unsafe.objectFieldOffset(ageField), 11);
        System.out.println(u1.age);
        //输出11
    }

    public static void normalThrow() throws IOException {
        throw new IOException();
    }

    public void unsafeThrow() {
        //不用try-catch或者在方法上声明throws
        Unsafe unsafe = getInstance();
        unsafe.throwException(new IOException());
    }


    /**
     * 使用堆外内存存储数组
     */
    @Test
    public void offHeap() {
        //int占用4个字节
        int INT = 4;
        //数组大小
        int size = 12;
        //内存中数组的地址
        long address;

        Unsafe unsafe = getInstance();
        address = unsafe.allocateMemory(size * INT);

        //设置指定下标位置的值
        unsafe.putInt(address + 1 * INT, 2);
        unsafe.putInt(address + 3 * INT, 99);

        //获取指定下标位置的值
        System.out.println(unsafe.getInt(address + 0 * INT));
        System.out.println(unsafe.getInt(address + 1 * INT));
        System.out.println(unsafe.getInt(address + 2 * INT));
        System.out.println(unsafe.getInt(address + 3 * INT));
        System.out.println(unsafe.getInt(address + 4 * INT));
        //记得释放内存
        unsafe.freeMemory(address);
//        System.out.println(unsafe.getInt(address + 1 * INT));
    }


    public void unsafeCAS() {
        //TODO 测试Counter
    }

    static class Counter {

        private volatile int count = 0;

        private static long offset;

        private static Unsafe unsafe;

        static {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                //因为是静态方法，所以不用传obj对象
                unsafe = (Unsafe) f.get(null);
                Field field = Counter.class.getDeclaredField("count");
                offset = unsafe.objectFieldOffset(field);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        public void increment() {
            int before = count;
            while (!unsafe.compareAndSwapInt(this, offset, before, before + 1)) {
                //重新读取最新值
                before = count;
            }
        }

        public int getCount() {
            return count;
        }
    }


}
