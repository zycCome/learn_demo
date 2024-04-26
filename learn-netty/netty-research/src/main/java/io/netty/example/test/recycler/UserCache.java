package io.netty.example.test.recycler;

import io.netty.util.Recycler;
import org.junit.Test;

/**
 * @author zhuyc
 * @date 2021/10/05 13:53
 **/
public class UserCache {

    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }

    };

    static final class User {
        private String name;
        private Recycler.Handle<User> handle;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public void recycle() {
            handle.recycle(this);
        }

    }

    /**
     * 最简单的测试
     * @param args
     */
    public static void main(String[] args) {

        User user1 = userRecycler.get(); // 1、从对象池获取 User 对象

        user1.setName("hello"); // 2、设置 User 对象的属性

        user1.recycle(); // 3、回收对象到对象池

        User user2 = userRecycler.get(); // 4、从对象池获取对象

        System.out.println(user2.getName());

        System.out.println(user1 == user2);

    }


    /**
     * 其他线程回收，debug回收逻辑
     */
    @Test
    public void test1() throws InterruptedException {

        User user1 = userRecycler.get(); // 1、从对象池获取 User 对象
        user1.setName("hello"); // 2、设置 User 对象的属性

        new Thread() {
            @Override
            public void run() {
                user1.recycle(); // 3、回收对象到对象池
                System.out.println("111");
            }
        } .start();


        User user2 = userRecycler.get(); // 4、从对象池获取对象
        System.out.println(user2.getName());
        System.out.println(user1 == user2);

        new Thread() {
            @Override
            public void run() {
                user2.recycle(); // 5、回收对象到对象池
                System.out.println("222");
            }
        } .start();

        System.out.println("end");
        Thread.sleep(1111111111);

    }

}

