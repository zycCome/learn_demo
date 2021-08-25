package com.zyc.learn_demo.concurrent;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * 自定义lock
 *
 * @author zhuyc
 * @date 2021/08/15 21:39
 **/
public class Lock {

    /**
     * 表示线程状态
     */
    private volatile int state;

    private static Unsafe unsafe;

    private static long stateOffset;

    private static long tailOffset;

    static {
        unsafe = getInstance();
        Field stateField = null;
        Field tailField = null;
        try {
            stateField = Lock.class.getDeclaredField("state");
            stateOffset = unsafe.objectFieldOffset(stateField);
            tailField = Lock.class.getDeclaredField("tail");
            tailOffset = unsafe.objectFieldOffset(tailField);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }


    private static Unsafe getInstance() {
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

    private static class Node {
        /**
         * 阻塞的线程
         */
        Thread thread;
        /**
         * 前一个线程
         */
        Node prev;
        /**
         * 后一个线程
         */
        Node next;

        public Node(Thread thread, Node prev) {
            this.thread = thread;
            this.prev = prev;
            //这里没有设置pre.next = this.只有等到CAS设置this为tail变量后，才算成功排队成功！
        }
    }

    //这里需要先初始化，在排队中。表示当前获取锁的那个node（真正的队头）
    private volatile Node tail = new Node(null,null);
    private volatile Node head = tail;

    private boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    public void lock() {
        // 尝试更小state字段，更新成功说明获取到了锁
        if (compareAndSetState(0, 1)) {
            return;
        }
        //未更新成功则入队
        Node node = enqueue();
        /*
         * 休眠前再次尝试获取锁
         * 原因是为了交叉校验，避免在排队过程中已经释放了锁
         */
        while (node.prev != head || !compareAndSetState(0, 1)) {
            unsafe.park(false, 0L);
        }
        // 下面的代码已经是线程安全的了，因为只有唤醒后成功获取锁才能走到这里
        // 变更head，head不是指代第一个排队的线程，而是最新一个通过排队成功获取锁的线程
        head = node;
        // 清空Node内容，协助GC
        node.thread = null;
        //将上一个节点从链表中剔除，并协助GC
        Node prev = node.prev;
        node.prev = null;
        /*
         * 前一个node的pre属性已经在lock方法中释放了，但next属性会延迟到next节点获取锁后才释放!
         * 为什么不能马上释放？
         * 因为next属性指向的node对应的线程在解锁时，需要唤醒。释放了，肯定找不到了！
         */
        prev.next = null;
    }

    /**
     * 当前线程入队
     *
     * @return
     */
    private Node enqueue() {
        while (true) {
            //获取尾节点
            Node t = tail;
            //每次都新建一个
            Node node = new Node(Thread.currentThread(), tail);
            //不断自旋重试
            if (compareAndSetTail(t, node)) {
                //只有在这里才能设置next属性，pre属性是不准的！！（比如上面cas失败的node会被丢失）
                t.next = node;
                return node;
            }
        }
    }

    public void unlock() {
        state = 0;
        //这里还是用到next属性，所以lock方法里面不能清空该属性
        Node next = head.next;
        if(next != null) {
            unsafe.unpark(next.thread);
        }
    }

    private static int count = 0;

    /**
     * 对比测试
     * 测试Lock的效果
     * @throws InterruptedException
     */
    @Test
    public void testLock() throws InterruptedException {
        Lock myLock = new Lock();
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        IntStream.range(0,1000).forEach(i -> new Thread(() -> {
            myLock.lock();
            try {
                IntStream.range(0,10000).forEach(j -> {
                    count++;
                });
            } finally {
                myLock.unlock();
            }
            countDownLatch.countDown();
        },"tt-"+i).start());

        countDownLatch.await();
        System.out.println(count);
    }

    private volatile static int count1 = 0;

    /**
     * 不用lock的效果
     * @throws InterruptedException
     */
    @Test
    public void testLock2() throws InterruptedException {
        Lock myLock = new Lock();
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        IntStream.range(0,1000).forEach(i -> new Thread(() -> {
            IntStream.range(0,10000).forEach(j -> {
                count1++;
            });
            countDownLatch.countDown();
        },"tt-"+i).start());

        countDownLatch.await();
        System.out.println(count1);
    }

}
