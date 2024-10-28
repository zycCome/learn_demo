package com.zyc.learn_demo.algorithm.timewheel2;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * 参考：https://juejin.cn/post/7083795682313633822
 */
@Data
@Slf4j
public class TimeWheel {

    /**
     * 基本时间跨度，时间轮一格的跨度
     */
    private long tickMs;
    /**
     * 时间单位个数
     */
    private int wheelSize;
    /**
     * 总体时间跨度
     *  等于tickMs * wheelSize;
     */
    private long interval;
    /**
     * 当前所处时间
     * 表示时间轮当前所处的时间，currentTime 是 tickMs 的整数倍。不一定指向时间轮的第一个格子
     */
    private long currentTime;
    /**
     * 定时任务列表
     */
    private TimerTaskList[] buckets;
    /**
     * 上层时间轮
     */
    private volatile TimeWheel overflowWheel;
    /**
     * 一个Timer只有一个DelayQueue,协助推进时间轮
     */
    private DelayQueue<TimerTaskList> delayQueue;


    public TimeWheel(long tickMs, int wheelSize, long currentTime, DelayQueue<TimerTaskList> delayQueue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.interval = tickMs * wheelSize;
        this.buckets = new TimerTaskList[wheelSize];
        /**
         * 目的：保持整数倍，减去余数
         * 比如当前 1688006789928，tickMs是100ms，则currentTime为1688006789900ms。
         */
        this.currentTime = currentTime - (currentTime % tickMs);
        log.info("currentTime:{}", DateUtil.format(new Date(this.currentTime),"yyyy-MM-dd HH:mm:ss.SSS"));
        this.delayQueue = delayQueue;
        for (int i = 0; i < wheelSize; i++) {
            // 初始化格子
            buckets[i] = new TimerTaskList();
        }
    }

    /**
     *
     * @param entry
     * @return
     */
    public boolean add(TimerTaskEntry entry) {
        long expiration = entry.getExpireMs();
//        log.info("tickMs + currentTime:{}",tickMs + currentTime);
        if (expiration < tickMs + currentTime) {
            // 定时任务到期，注意判断的是当前槽的结束时间
            log.info("============={}任务马上执行,过期时间:{},currentTime:{}",entry.getTimerTask().getDesc(),DateUtil.format(new Date(entry.getExpireMs()),"yyyy-MM-dd HH:mm:ss.SSS"),currentTime);
            return false;
        } else if (expiration < currentTime + interval) {
            // 扔进当前时间轮的某个槽里,只有时间大于某个槽,才会放进去
            long virtualId = (expiration / tickMs);
            int index = (int) (virtualId % wheelSize);
//            log.info("expiration:{},virtualId:{}",expiration,virtualId);
            TimerTaskList bucket = buckets[index];
            bucket.addTask(entry);

            log.info("============={}任务加到队列,过期时间:{}",entry.getTimerTask().getDesc(),DateUtil.format(new Date(entry.getExpireMs()),"yyyy-MM-dd HH:mm:ss.SSS"));
            // 设置bucket 过期时间
            /**
             * 比如这里virtualId最小是1，因此对应槽会在 1个tickMs时执行（不是相对时间，而是绝对时间）。但不是非常精确的！！！
             * 比如tickeMs为100毫秒，当前是xxxx067毫秒，则下一次执行应该是在xxxx167，而不是实际的xxxx100
             */
            if (bucket.setExpiration(virtualId * tickMs)) {
                // 设好过期时间的bucket需要入队
                /**
                 * 重复插入会怎么样？比如在115ms设置200ms后执行，在216ms设置100ms后执行，都是对应这个槽
                 * 会重复取出这个槽！！所以需要顺序或者加锁处理
                 * 有没有办法优化成不重复添加。因此bucket要复用，只能判断一次，或者在bucket上打标记
                 */
                if(delayQueue.contains(bucket)) {
                    log.info("repeat insert!!!");
                }
//                log.info("bucket index:{}",index);
                log.info("bucket 过期时间:{}",DateUtil.format(new Date(bucket.getExpiration()),"yyyy-MM-dd HH:mm:ss.SSS"));
                delayQueue.offer(bucket);
                return true;
            } else {
                log.info("setExpiration false");
                // fix bug 1：之前这里不返回ture，导致最终返回false，导致任务即添加到了队列里面（未来执行），又会自动执行一次
                return true;
            }
        } else {
            log.info("upper wheel");
            // 当前轮不能满足,需要扔到上一轮
            TimeWheel timeWheel = getOverflowWheel();
            // 递归处理。如果上层也不行，就上上层
            return timeWheel.add(entry);
        }
//        return false;
    }

    private TimeWheel getOverflowWheel() {
        if (overflowWheel == null) {
            // 上锁避免并发
            synchronized (this) {
                if (overflowWheel == null) {
                    /**
                     * 1. interval 作为 tickMs
                     * 2. delayQueue 复用，上层时间轮并没有用新的delayQueue
                     * 3. currentTime 传递子时间轮的currentTime。用时钟举例，当前时间00:34:23, 120s后的任务，当前层currentTime是15:34:23，因此上一层的currentTime是00:34:00
                     *  即 所有层级的时间轮的currentTime 是对应在一个时间段的。比如更上层就是00:00:00了。
                      */
                    overflowWheel = new TimeWheel(interval, wheelSize, currentTime, delayQueue);
                }
            }
        }
        return overflowWheel;
    }

    /**
     * 推进指针，不需要移动槽的位置，因为槽是循环的
     *
     * 比如 currentTime为0时，1-59可用，推进currentTime为20时，21-19都是可用的，表示后59秒，时间轮的上下限就是currentTime+interval
     *
     * @param timestamp
     */
    public void advanceLock(long timestamp) {
        /**
         * fixbug3：
         * 等于时也应该推进，因为范围是前闭后开。因此等于上限时也要推进指针
         *
         * 如果某个list的过期时间已经大于当前时间轮currentTime对应的格子了，则马上推进，不会等等整个轮子时间都结束再推进
         */
        if (timestamp >= currentTime + tickMs) {
//        if (timestamp > currentTime + tickMs) {
            // 推进currentTime
            currentTime = timestamp - (timestamp % tickMs);
            if (overflowWheel != null) {
                // 递归推进上层，传递是同一个timestamp，因此所有的时间轮的currentTime 是永远对应的
                this.getOverflowWheel().advanceLock(timestamp);
            }
        }
    }

}
