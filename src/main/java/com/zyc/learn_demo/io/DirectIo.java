package com.zyc.learn_demo.io;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/26 10:21 AM
 * @Version 1.0.0
 **/
public class DirectIo {

    @Test
    public void byteBufferTest() {
        // 分配 10M 堆外内存
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
        // 释放堆外内存
        ((DirectBuffer) byteBuffer).cleaner().clean();
    }

}
