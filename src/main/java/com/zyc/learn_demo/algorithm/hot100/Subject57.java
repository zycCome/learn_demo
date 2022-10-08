package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 插入区间
 *
 * @author zhuyc
 * @date 2022/10/02 21:33
 **/
public class Subject57 {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        if(intervals.length == 0) {
            return new int[][] {newInterval};
        }
        List<int[]> result = new ArrayList<>();
        int[] pre = null;
        boolean startMerge = false;
        for (int i = 0; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if(newInterval[0] <= interval[0]) {
                startMerge = true;
                // 如果有需要，先处理新增的数组
                if(pre == null) {
                    result.add(newInterval);
                    pre = newInterval;
                }
                if(newInterval[0] <= pre[1]) {
                    // 合并区间
                    pre[1] = Math.max(pre[1],newInterval[1]);
                } else {
                    result.add(newInterval);
                    pre = newInterval;
                }
            }
            if(pre == null) {
                result.add(interval);
                pre = interval;
            }
            if(interval[0] <= pre[1]) {
                // 合并区间
                pre[1] = Math.max(pre[1],interval[1]);
            } else {
                result.add(interval);
                pre = interval;
                if(startMerge) {
                    // 剩下的直接加入
                    for(int j = i+1;j < intervals.length;j++) {
                        result.add(intervals[j]);
                    }
                    break;
                }
            }
        }
        if(!startMerge) {
            if(newInterval[0] <= pre[1]) {
                // 合并区间
                pre[1] = Math.max(pre[1],newInterval[1]);
            } else {
                result.add(newInterval);
                pre = newInterval;
            }
        }
        return result.toArray(new int[result.size()][]);

    }

    @Test
    public void test() {
        int[][] intervals = {{1,5}};
        int[] newInterval = {2,7};
        for (int[] ints : insert(intervals, newInterval)) {
            for (int anInt : ints) {
                System.out.print(anInt);
                System.out.print("\t");
            }
            System.out.println("--");
        }
    }

    @Test
    public void test2() {
        int[][] intervals = {{1,5}};
        int[] newInterval = {0,0};
        for (int[] ints : insert(intervals, newInterval)) {
            for (int anInt : ints) {
                System.out.print(anInt);
                System.out.print("\t");
            }
            System.out.println("--");
        }
    }

}
