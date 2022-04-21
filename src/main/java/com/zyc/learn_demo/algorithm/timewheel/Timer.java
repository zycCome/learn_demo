package com.zyc.learn_demo.algorithm.timewheel;

/**
 * kafka中提供了Timer接口，用于对外提供调用，分别是Timer#add添加任务；Timer#advanceClock驱动时间； Timer#size时间轮中总任务数；Timer#shutdown停止时间轮
 *
 *
 * @author zhuyc
 * @date 2022/04/21 17:08
 **/
public interface Timer {
    void add(TimerTask timerTask);
    boolean advanceClock(Long timeoutMs) throws Exception;
    int size();
    void shutdown();
}
