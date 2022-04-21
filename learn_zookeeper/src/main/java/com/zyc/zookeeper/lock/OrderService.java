package com.zyc.zookeeper.lock;

/**
 * @author zhuyc
 * @date 2022/04/21 10:50
 **/
public class OrderService {

    private static class OrderNumGeneratorService implements Runnable {

        private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();;
        private Lock lock = new ZooKeeperDistrbuteLock();

        @Override
        public void run() {
            lock.getLock();
            try {
                System.out.println(Thread.currentThread().getName() + ", 生成订单编号："  + orderNumGenerator.getOrderNumber());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("----------生成唯一订单号----------");
        for (int i = 0; i < 3; i++) {
            new Thread(new OrderNumGeneratorService()).start();
        }

        Thread.sleep(1000000);
    }


}
