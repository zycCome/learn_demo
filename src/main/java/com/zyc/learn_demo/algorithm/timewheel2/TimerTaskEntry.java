package com.zyc.learn_demo.algorithm.timewheel2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务项（TimerTaskEntry），其中封装了真正的定时任务 TimerTask
 */
@Data
@Slf4j
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    volatile TimerTaskList timedTaskList;
    TimerTaskEntry next;
    TimerTaskEntry prev;
    private TimerTask timerTask;


    /**
     * 绝对时间戳
     */
    private long expireMs;

    public TimerTaskEntry(TimerTask timedTask, long expireMs) {
        this.timerTask = timedTask;
        this.expireMs = expireMs;
        this.next = null;
        this.prev = null;
    }


    /**
     * 什么作用？
     * 会复用吗？
     *
     */
    void remove() {
        TimerTaskList currentList = timedTaskList;
        while (currentList != null) {
            currentList.remove(this);
            currentList = timedTaskList;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        return ((int) (this.expireMs - o.expireMs));
    }

}

