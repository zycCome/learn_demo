package com.zyc.learn_demo.algorithm.timewheel2;

/**
 * @Description https://juejin.cn/post/7083795682313633822
 * @Author zilu
 * @Date 2023/6/29 11:31 AM
 * @Version 1.0.0
 **/
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class TimerLauncher implements Timer {

    /**
     * 底层时间轮。是定时器某种实现方式中的组件
     */
    private TimeWheel timeWheel;
    /**
     * 一个Timer只有一个延时队列
     */
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
    /**
     * 过期（到期）任务执行线程
     */
    private ExecutorService workerThreadPool;
    /**
     * 轮询delayQueue获取过期任务线程
     */
    private ExecutorService bossThreadPool;


    public TimerLauncher() {
        this.timeWheel = new TimeWheel(50, 20, System.currentTimeMillis(), delayQueue);
        this.workerThreadPool = Executors.newFixedThreadPool(100);
        this.bossThreadPool = Executors.newFixedThreadPool(1);
        /**
         * currentTime不一定和现实时间的槽是对应的
         * 比如先添加了一个2小时3分钟6秒的任务，时间轮是时钟格式，此时currentTime为0
         *
         * 1s后，又添加了一个3s后的任务，应该在4s时执行,得到槽位4，并且槽4在DelayQueue中执行时间也是绝对时间4s减去当前时间1是，即3s后执行槽里的所有内容，因此没有问题
         * 如果添加了一个2分钟后执行的，则会添加到上一个时间轮
         *
         */
        this.bossThreadPool.submit(() -> {
            while (true) {
                // 死循环，一直尝试从delayQueue中获取任务。20ms该值可变，甚至是更大的值
//                this.advanceClock(20);
                this.advanceClock(2000000);
            }
        });
    }


    public void addTimerTaskEntry(TimerTaskEntry entry) {
        //
        if (!timeWheel.add(entry)) {
            // 任务已到期
            TimerTask timerTask = entry.getTimerTask();
            log.info("=====任务:{} 已到期,准备执行============", timerTask.getDesc());
            workerThreadPool.submit(timerTask);
        }
    }


    @Override
    public synchronized void add(TimerTask timerTask) {

        // 新建了TimerTaskEntry，因此TimerTaskEntry理论上不会复用
        TimerTaskEntry entry = new TimerTaskEntry(timerTask, timerTask.getDelayMs() + System.currentTimeMillis());
        log.info("=======添加任务开始====task:{},预期执行时间:{}", timerTask.getDesc(),DateUtil.format(new Date(entry.getExpireMs()),"yyyy-MM-dd HH:mm:ss.SSS"));

        timerTask.setTimerTaskEntry(entry);
        addTimerTaskEntry(entry);
    }

    /**
     * 推动指针运转获取过期任务
     *
     * @param timeout 时间间隔
     * @return
     */
    @Override
    public void advanceClock(long timeout) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                // 推进时间
                timeWheel.advanceLock(bucket.getExpiration());
                // 执行过期任务(包含降级)
                /**
                 * 分两种情况：
                 * 1. bucket最小粒度的时间轮的格子，那么就执行
                 * 2. 上层时间轮的格子，将其中所有任务降级到子时间轮
                 */
                /**
                 * fixbug2：
                 * currentTime为0，tickMs为50ms，add任务c在60ms执行。
                 * c在取整处理后会在50ms后执行，然后c所在的bucket从DelayQueue中取出后，会尝试再次添加，
                 * 再次添加时，又回加到队列里面取，导致队列一直消费不完。为什么会重复加入，不应该加入失败吗？为什么currentTime没有推进？所以根因是advanceLock有bug，具体看advanceLock方法的注射
                 */
                bucket.clear(this::addTimerTaskEntry);
            }
            // 没有bucket要执行，直接结束

        } catch (InterruptedException e) {
            log.error("advanceClock error");
        }
    }

    @Override
    public int size() {
        //TODO 未实现
        return 10;
    }

    @Override
    public void shutdown() {
        this.bossThreadPool.shutdown();
        this.workerThreadPool.shutdown();
        this.timeWheel = null;
    }

}

