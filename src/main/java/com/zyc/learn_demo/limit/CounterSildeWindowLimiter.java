package com.zyc.learn_demo.limit;


/**
 * Project: AllForJava
 * Title:
 * Description:
 * Date: 2020-09-07 18:38
 * Copyright: Copyright (c) 2020
 *
 * @公众号: 超悦编程
 * @微信号：exzlco
 * @author: 超悦人生
 * @email: exzlc@139.com
 * @version 1.0
 **/

public class CounterSildeWindowLimiter {

    private int windowSize; //窗口大小，毫秒为单位
    private int limit;//窗口内限流大小
    private int splitNum;//切分小窗口的数目大小
    private int[] counters;//每个小窗口的计数数组
    private int index;//当前小窗口计数器的索引(整个时间窗口中的最后一个窗口)
    private long startTime;//第一个小窗口开始时间
    private long splitTimeSize;//小窗口的时间长度

    private CounterSildeWindowLimiter(){}

    public CounterSildeWindowLimiter(int windowSize, int limit, int splitNum){
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new int[splitNum];
        index = 0;
        splitTimeSize = windowSize/splitNum;
        startTime = System.currentTimeMillis();

    }

    //请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public synchronized boolean tryAcquire(){
        long curTime = System.currentTimeMillis();
        //获取当前应该所在的小窗口下标
        long l = (curTime - startTime) / splitTimeSize;

        slideWindow(l);//滑动窗口
        int count = 0;
        for(int i = 0;i < splitNum;i ++){
            count += counters[i];
        }
        if(count >= limit){
            return false;
        }else{
            int countIndex = (int) l%splitNum;
            counters[countIndex] ++;
            System.out.println(splitNum +"|"+counters[countIndex]+"|"+countIndex);
            return true;
        }
    }

    private synchronized void slideWindow(long windowsNum){
        if(windowsNum < splitNum)
            return;
        //这种方式好，自动纠正。不依赖定时器。
        //废弃的窗口数量，最多也就splitNum个
        long needSlide = windowsNum - splitNum + 1;
        long slideNum = Math.min( needSlide ,splitNum);
        for(int i = 0;i < slideNum;i ++){
            //先将第一个窗口重置
            counters[index] = 0;
            index = (index + 1) % splitNum;
        }
        startTime = startTime + needSlide * (splitTimeSize);//更新滑动窗口时间
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求
        int limit = 100;
        CounterSildeWindowLimiter counterSildeWindowLimiter = new CounterSildeWindowLimiter(1000,limit,10);
        int count = 0;

        //计数器滑动窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器滑动窗口算法测试开始");
        //滑动窗口的一秒钟不会出现边界问题。因为这个是最近的一秒钟。
        System.out.println("开始模拟100组间隔150ms的50次请求");
        int faliCount = 0;
        for(int j = 0;j < 1;j ++){
            //每一组内通过的请求
            count = 0;
            for(int i = 0;i < 200;i ++){
                if(counterSildeWindowLimiter.tryAcquire()){
                    count ++;
                } else {
                    System.out.println("------");
                }
                Thread.sleep(6);
            }

            System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
            Thread.sleep((int)(Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
        System.out.println("===========================================================================================");


//        //计数器固定窗口算法模拟100组间隔30ms的50次请求
//        System.out.println("计数器固定窗口算法测试开始");
//        //模拟100组间隔30ms的50次请求
//        CounterLimiter counterLimiter = new CounterLimiter(1000,limit);
//        System.out.println("开始模拟100组间隔150ms的50次请求");
//        faliCount = 0;
//        for(int j = 0;j < 100;j ++){
//            count = 0;
//            for(int i = 0;i < 50;i ++){
//                if(counterLimiter.tryAcquire()){
//                    count ++;
//                }
//            }
//            Thread.sleep(150);
//            //模拟50次请求，看多少能通过
//            for(int i = 0;i < 50;i ++){
//                if(counterLimiter.tryAcquire()){
//                    count ++;
//                }
//            }
//            if(count > limit){
//                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
//                faliCount ++;
//            }
//            Thread.sleep((int)(Math.random() * 100));
//        }
//        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
    }
}


