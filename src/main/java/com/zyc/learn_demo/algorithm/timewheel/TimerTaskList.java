package com.zyc.learn_demo.algorithm.timewheel;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 *
 * 在时间轮中每个槽代表一个列表，即TimerTaskList，每个TimerTaskList中包含多个TimerTaskEntry，并且用双向列表链接。
 * TimerTaskList实现了Delayed接口，用于返回剩余的时间，把上层时间轮的任务移动位置。
 *
 * @author zhuyc
 * @date 2022/04/21 16:53
 **/
public class TimerTaskList implements Delayed {
    //当前列表中包含的任务数
    private AtomicInteger taskCounter;
    // 列表的头结点
    private TimerTaskEntry root;
    // 当前槽的过期时间
    private AtomicLong expiration = new AtomicLong(-1L);


    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.root =  new TimerTaskEntry(null,-1L);
        root.next = root;
        root.prev = root;
    }

    // 给当前槽设置过期时间。也就是槽的开始时间
    public boolean setExpiration(Long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    public Long getExpiration() {
        return expiration.get();
    }

    // 用于遍历当前列表中的任务
    public synchronized  void foreach(Consumer<TimerTask> f) {
        TimerTaskEntry entry = root.next;
        while(entry != root) {
            TimerTaskEntry nextEntry = entry.next;
            if(!entry.cancel()) {
                f.accept(entry.timerTask);
            }
            entry = nextEntry;
        }
    }

    // 添加任务到列表中
    public void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while(!done) {
            timerTaskEntry.remove();

            synchronized (this) {
                synchronized (timerTaskEntry) {
                    if(timerTaskEntry.list == null) {
                        TimerTaskEntry tail = root.prev;
                        timerTaskEntry.next = root;
                        timerTaskEntry.prev = tail;
                        timerTaskEntry.list = this;
                        tail.next = timerTaskEntry;
                        root.prev = timerTaskEntry;
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    //移出任务
    private synchronized void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (timerTaskEntry) {
            if(timerTaskEntry.list == this) {
                timerTaskEntry.next.prev = timerTaskEntry.prev;
                timerTaskEntry.prev.next = timerTaskEntry.next;
                timerTaskEntry.next = null;
                timerTaskEntry.prev = null;
                timerTaskEntry.list = null;
                // 计数减1
                taskCounter.decrementAndGet();
            }
        }
    }

    /**
     * 冲刷槽，将当前槽的所有元素都依次应用到Consumer中
     * @param f
     */
    public synchronized void flush(Consumer<TimerTaskEntry> f) {
        TimerTaskEntry head = root.next;
        // 因为head的next也是head
        while(head != root) {
            remove(head);
            f.accept(head);
            head = root.next;
        }
        // 当前槽重置。expiration也会影响Delayed接口实现
        expiration.set(-1L);
    }
    //获得当前任务剩余时间
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(),0),TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed d) {
        TimerTaskList other = (TimerTaskList) d;
        return Long.compare(getExpiration(),other.getExpiration());
    }

    /**
     * TimerTaskEntry是TimerTask的包装,实现了Comparable接口，用来比较两个任务的过期时间，以决定任务list插入的顺序
     *
     * @author zhuyc
     * @date 2022/04/21 16:52
     **/
     static class TimerTaskEntry implements Comparable<TimerTaskEntry>{
        //包含一个任务
        public TimerTask timerTask;
        // 任务的过期时间，此处的过期时间设置的过期间隔+系统当前时间（毫秒）
        public Long expirationMs;

        // 当前任务属于哪一个列表
        private TimerTaskList list;
        // 当前任务的上一个任务，用双向列表连接
        private TimerTaskEntry prev;
        private TimerTaskEntry next;


        public TimerTaskEntry(TimerTask timerTask,Long expirationMs) {
            this.timerTask = timerTask;
            this.expirationMs = expirationMs;
            // 传递进来任务TimerTask，并设置TimerTask的包装类
            if(timerTask != null) {
                timerTask.setTimerTaskEntry(this);
            }
        }

        // 任务的取消，就是判断任务TimerTask的Entry是否是当前任务
        public boolean cancel() {
            return timerTask.getTimerTaskEntry() != this;
        }

        // 任务的移出
        public void remove() {
            TimerTaskList currentList = list;
            while(currentList != null) {
                currentList.remove(this);
                currentList = list;
            }
        }
        // 比较两个任务在列表中的位置，及那个先执行
        @Override
        public int compareTo(TimerTaskEntry that) {
            return Long.compare(expirationMs,that.expirationMs);
        }
    }

}




