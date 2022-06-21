package com.zyc.mybatis.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * @Description TODO
 * @Author zilu
 * @Date 2022/6/13 2:11 PM
 * @Version 1.0.0
 **/
public class SnowflakeIdWorker {

    private static final long twepoch = 1561910400000L;
    private static final long timeBit = -4194304L;
    private final long workerIdBits;
    private final long datacenterIdBits;
    private final long maxWorkerId;
    private final long maxDatacenterId;
    private final long sequenceBits;
    private final long workerIdShift;
    private final long datacenterIdShift;
    private final long timestampLeftShift;
    private final long sequenceMask;
    private long workerId;
    private long datacenterId;
    private long sequence;
    private long initSequence;
    private long lastTimestamp;

    public SnowflakeIdWorker(long workerId) {
        this(workerId, 0L);
    }

    public SnowflakeIdWorker(long workerId, long datacenterId) {
        this.workerIdBits = 10L;
        this.datacenterIdBits = 0L;
        this.maxWorkerId = 1023L;
        this.maxDatacenterId = 0L;
        this.sequenceBits = 12L;
        this.workerIdShift = 12L;
        this.datacenterIdShift = 22L;
        this.timestampLeftShift = 22L;
        this.sequenceMask = 4095L;
        this.lastTimestamp = -1L;
        if (workerId <= 1023L && workerId >= 0L) {
            if (datacenterId <= 0L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = 0L;
                this.initSequence = (long)RandomUtils.nextInt(0, 2000);
                this.sequence = this.initSequence;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 0L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 1023L));
        }
    }

    private synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = this.initSequence;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1561910400000L << 22 | this.datacenterId << 22 | this.workerId << 12 | this.sequence;
        }
    }

    public Date getTime(long id) {
        return new Date(((-4194304L & id) >> 22) + 1561910400000L);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public Long getId() {
        return this.nextId();
    }

    public Long getDescId() {
        return Long.MAX_VALUE - this.nextId();
    }

    public Long getTimeId(long time) {
        if (time < 1561910400000L) {
            time = 1561910400000L;
        }

        return Long.MAX_VALUE - (time - 1561910400000L << 22);
    }

    public String getRandomStr() {
        return Long.toString(this.nextId(), 36);
    }
}
