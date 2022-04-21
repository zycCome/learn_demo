package com.zyc.learn_demo.algorithm.timewheel;


/**
 * 参考：https://www.jianshu.com/p/0dc512ec0138
 *
 * 表示一个要执行的任务，实现了Runnable接口
 *
 * @author zhuyc
 * @date 2022/04/21 16:51
 **/
public abstract class TimerTask implements Runnable {

    public long delayMs; //表示当前任务延迟多久后执行(单位ms)，比如说延迟3s，则此值为3000

    public TimerTask(long delayMs) {
        this.delayMs =  delayMs;
    }
    // 指向TimerTaskEntry对象，一个TimerTaskEntry包含一个TimerTask，TimerTaskEntry是可复用的
    private TimerTaskList.TimerTaskEntry timerTaskEntry = null;

    // 取消当前任务，就是从TimerTaskEntry移出TimerTask，并且把当前的timerTaskEntry置空
    public synchronized void cancel() {
        if(timerTaskEntry != null) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = null;
    }

    //设置当前任务绑定的TimerTaskEntry
    public synchronized void setTimerTaskEntry(TimerTaskList.TimerTaskEntry entry) {
        if(timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    public TimerTaskList.TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }
}