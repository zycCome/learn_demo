package com.zyc.learn_demo.algorithm.timewheel2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;


/**
 * 对应时间轮上的bucket
 * 为什么实现Delayed的？避免空推进，只在槽有任务时推进。
 */
@Data
@Slf4j
public class TimerTaskList implements Delayed {

    /**
     * TimerTaskList 环形链表使用一个虚拟根节点root
     */
    private TimerTaskEntry root = new TimerTaskEntry(null, -1);
    /**
     * bucket的过期时间
     * 是绝对时间，而不是一个相对时间
     */
    private AtomicLong expiration = new AtomicLong(-1L);

    {
        root.next = root;
        root.prev = root;
    }

    public long getExpiration() {
        return expiration.get();
    }

    /**
     * 设置bucket的过期时间,设置成功返回true
     *
     * @param expirationMs
     * @return
     */
    boolean setExpiration(long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    /**
     * TODO 这里有个问题！
     * 边界情况下，bucket对应的expiration改变了（比如某个格子本来表示第59s，后来表示第119s），但是第59的任务列表还没消费怎么办？
     * 这时第119秒的任务也放入了这个bucket。这个问题的本质是时间推进的过程中，bucket列表还没来得及处理，并不是一个干净的列表（有一些脏数据在）
     * @param entry
     * @return
     */
    public boolean addTask(TimerTaskEntry entry) {
        boolean done = false;
        while (!done) {
            // 如果TimerTaskEntry已经在别的list中就先移除,同步代码块外面移除,避免死锁,一直到成功为止
            entry.remove();
            synchronized (this) {
                if (entry.timedTaskList == null) {
                    // 加到链表的末尾，有哨兵节点就是方便一些。不用考虑第一个节点的特殊场景
                    // entry引用list
                    entry.timedTaskList = this;
                    TimerTaskEntry tail = root.prev;
                    entry.prev = tail;
                    entry.next = root;
                    tail.next = entry;
                    root.prev = entry;
                    done = true;
                } else {
                    log.error("unknow 1");
                }
            }
        }
        return true;
    }

    /**
     * 从 TimedTaskList 移除指定的 timerTaskEntry
     *
     * @param entry
     */
    public void remove(TimerTaskEntry entry) {
        synchronized (this) {
            if (entry.getTimedTaskList().equals(this)) {
                entry.next.prev = entry.prev;
                entry.prev.next = entry.next;
                entry.next = null;
                entry.prev = null;
                entry.timedTaskList = null;
            }
        }
    }

    /**
     * 移除所有
     * 锁是槽级别
     */
    public synchronized void clear(Consumer<TimerTaskEntry> entry) {
        TimerTaskEntry head = root.next;
        while (!head.equals(root)) {
            // 消费前，移除，避免重复消费
            remove(head);
            entry.accept(head);
            head = root.next;
        }
        expiration.set(-1L);
    }


    /**
     * 返回延迟多久后执行
     * @param unit the time unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long bucketExpiration = expiration.get();
        long delay = Math.max(0, unit.convert( bucketExpiration - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
//        if(delay > 9000000) {
//            log.info("who call me？");
//        }
//        log.info("bucketExpiration:{},delayTime:{}",bucketExpiration,delay);
        return delay;
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList) {
            return Long.compare(expiration.get(), ((TimerTaskList) o).expiration.get());
        }
        return 0;
    }

}

