package com.zyc.learn_demo.algorithm.timewheel2;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zilu
 * @Date 2023/6/29 11:04 AM
 * @Version 1.0.0
 **/
public class DelayQueueTest {


    public static void main(String[] args) throws InterruptedException {

        DelayObj delayObj = new DelayObj(1);
        DelayObj delayObj2 = new DelayObj(5);

        DelayQueue<DelayObj> delayQueue = new DelayQueue<DelayObj>();
        //重复添加同一个元素，观察是否会取出两次。结论：会重复取出两次
        delayQueue.add(delayObj);
        delayQueue.add(delayObj);

        long l1 = System.currentTimeMillis();
        DelayObj poll1 = delayQueue.take();
        long l2 = System.currentTimeMillis();
        System.out.println((l2-l1));  //998(1秒)

        DelayObj poll2 = delayQueue.take();
        long l3 = System.currentTimeMillis();
        System.out.println((l3-l1));  //
    }



    public static class DelayObj implements Delayed {

        private volatile long second;

        public DelayObj(long second){
            this.second = System.currentTimeMillis() + second*1000;
        }

        public void setTime(long nanos) {
            second = nanos;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return second - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this) { // compare zero ONLY if same object
                return 0;
            }
            if (other instanceof DelayObj) {
                DelayObj x = (DelayObj)other;
                long diff = second - x.second;
                if (diff < 0) {
                    return -1;
                } else if (diff > 0) {
                    return 1;
                } else {
                    return 1;
                }
            }        long d = (getDelay(TimeUnit.NANOSECONDS) -
                    other.getDelay(TimeUnit.NANOSECONDS));


            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }


    }



}
