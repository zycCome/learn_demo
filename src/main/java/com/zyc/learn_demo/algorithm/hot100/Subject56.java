package com.zyc.learn_demo.algorithm.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合并区间
 *
 * @author zhuyc
 * @date 2022/10/02 21:15
 **/
public class Subject56 {

    /**
     * 先按区间按开始时间排序，然后判断前后相邻的两个区间能否合并
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        List<int[]> arrays = Arrays.stream(intervals).sorted(Comparator.comparingInt(a -> a[0])).collect(Collectors.toList());
        List<int[]> result = new ArrayList<>();
        int[] pre = null;
        for (int[] item : arrays) {
            if(pre == null) {
                result.add(item);
                pre = item;
            }
            if(item[0] <= pre[1]) {
                pre[1] = Math.max(pre[1],item[1]);
            } else {
                result.add(item);
                pre = item;
            }
        }

        return result.toArray(new int[result.size()][]);
    }

}
