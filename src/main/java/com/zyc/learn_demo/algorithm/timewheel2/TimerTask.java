package com.zyc.learn_demo.algorithm.timewheel2;

/**
 * @Description
 * @Author zilu
 * @Date 2023/6/29 10:40 AM
 * @Version 1.0.0
 **/
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class TimerTask implements Runnable {

    /**
     * 延时时间，单位毫秒
     */
    private long delayMs;
    /**
     * 任务所在的entry
     * 互相引用
     */
    private TimerTaskEntry timerTaskEntry;

    private String desc;

    public TimerTask(String desc, long delayMs) {
        this.desc = desc;
        this.delayMs = delayMs;
        this.timerTaskEntry = null;
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        // 如果这个TimerTask已经被一个已存在的TimerTaskEntry持有,先移除一个
        // 以新的为准
        if (timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    @Override
    public void run() {
        log.info("============={}任务执行", desc);
    }

}

