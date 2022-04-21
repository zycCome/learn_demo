package com.zyc.learn_demo.algorithm.timewheel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 时间轮TimeWheel代表一层时间轮，即第一层时间轮表示20ms，主要功能是添加任务和，驱动时间轮向前。
 *
 * @author zhuyc
 * @date 2022/04/21 17:05
 **/
public class TimingWheel {

    private Long tickMs;  //每一个槽表示的时间范围
    private Integer wheelSize; // 时间轮大小，即每一层时间轮的大小
    private Long startMs; // 系统的启动时间
    private AtomicInteger taskCounter;  // 当前层任务数
    private DelayQueue<TimerTaskList> queue; //延迟队列，用于从队列取每个任务列表

    private Long interval; //每一层时间轮代表的时间
    private List<TimerTaskList> buckets;  // 每一层的每一个槽中的时间任务列表
    private Long currentTime;  // 修正后的系统启动时间

    private TimingWheel overflowWheel = null;  // 上一层时间轮

    public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;
        interval = tickMs * wheelSize;
        currentTime = startMs - (startMs % tickMs); //当前时间，往前推

        buckets = new ArrayList<>(wheelSize);
        for(int i = 0;i < wheelSize;i++) {
            buckets.add(new TimerTaskList(taskCounter));  //创建每一个槽中的列表
        }
    }


    /**
     * 创建上层时间轮
     * 注意： 第一次创建时，上层时间轮的第一个槽对应下层时间轮的所有槽
     */
    public synchronized void addOverflowWheel() {
        if(overflowWheel == null) {
            overflowWheel = new TimingWheel(
                    interval,  // 此处interval即表示上一层时间轮表示的范围
                    wheelSize,
                    currentTime, // currentTime 传递过期了
                    taskCounter,
                    queue
            );
        }
    }

    /**
     * 返回false表示需要立即执行
     *
     * 注意：一个任务对应到最小轮的哪一个槽，即任务创建后就固定不变了。currentTime的推进/改变 根本不会影响对应到哪个槽
     * 影响的是什么？ 比如秒轮（一轮60s）。当t=120s，永远都是对应到秒轮的0槽（0-59）。但是只有 141 <= currentTime<= 199 时，才会对应到这里
     *
     * @param timerTaskEntry
     * @return
     */
    public boolean add(TimerTaskList.TimerTaskEntry timerTaskEntry) {
        Long expiration = timerTaskEntry.expirationMs;

        Long thisTime = currentTime + tickMs;
        // 任务是否已经取消，取消则返回
        if(timerTaskEntry.cancel()) {
            return false;
            // 当前任务是否已经过期，如果过期则返回false，要立即执行
        }else if(expiration < currentTime + tickMs) {
            return false;
            // 判断当前任务能否在添加到当前时间轮
        }else if(expiration < currentTime + interval) {

            Long virtualId = expiration / tickMs;
            // 计算当前任务要分配在哪个槽中
            // 类比手表，和滑动窗口的相对位置完全不一样
            int whereBucket = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets.get((int)(virtualId % wheelSize));

            bucket.add(timerTaskEntry);

            long bucketExpiration = virtualId * tickMs;
            //更新槽的过期时间，添加入延迟队列
            if(bucket.setExpiration(virtualId * tickMs)) {
                queue.offer(bucket);
            }
            return true;
        }else {
            //添加任务到高层时间轮
            if(overflowWheel == null) addOverflowWheel();
            return overflowWheel.add(timerTaskEntry);
        }
    }


    /**
     * 尝试向前驱动时间，只要过了当前的一个槽（不是需要过整个当前轮的所有槽），这也才能复用到每一个槽
     * 依次可能推进多个时间轮，而且是从低层次时间轮先改，后改高一层次时间轮
     *
     * 举例： 按秒-分-时粒度的时间轮。初始秒时间轮0
     * 0秒时加入一个任务 2小时3分钟2秒 时执行。
     * 这时会创建3个时间轮。且currentTime都是0，该任务在时级别的时间轮的第三个槽（对应范围2小时-3小时）。这个槽会加入DelayQueue
     * 当达到2小时3分钟2秒时刻，该槽被取出。然后开始推进时间，
     *
     * 这时：
     * 秒轮，2小时3分钟2秒时刻 >= 0 + 1秒 ，推进currentTime
     * 分轮：2小时3分钟2秒时刻 >= 0 + 1分钟，推进currentTime
     * 时轮：2小时3分钟2秒时刻 >= 0 + 1小时，推进currentTime
     *
     * 注意：一个任务对应到最小轮的哪一个槽，即任务创建后就固定不变了。currentTime的推进/改变 根本不会影响对应到哪个槽
     * @param timeMs
     */
    public void advanceClock(Long timeMs) {
        // 当前槽开始时间 大于等于 currentTime所对应槽的截止时间，因此需要推动时间轮
        if(timeMs >= currentTime + tickMs) {
            // 将当前槽的开始时间作为currentTime
            // 通过推荐当前时间轮的currentTime。可以让buckets数组达到循环链表的感觉
            /*
             * 配合add时 expiration 比较 currentTime + (tickMs/interval)
             * 比如 currentTime = 0s 时，tickMs = 1s , interval = 60s
             * 插入task1， expiration = 59s , 那么就会插入槽 59
             * 当 currentTime推进后变成1s.那么插入task2， expiration = 59s，此时会插入槽0.如果没推进，会插入到高层时间轮
             */
            currentTime = timeMs - (timeMs % tickMs);

            if(overflowWheel != null) {
                // 注意timeMs是一个绝对时间，当大于等于 高一级时间轮startTime + ticketMs(即一个槽的结束时间后)，就会继续推进
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
