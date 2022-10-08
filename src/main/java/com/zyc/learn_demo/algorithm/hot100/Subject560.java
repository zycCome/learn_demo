package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @author zhuyc
 * @date 2022/10/03 19:51
 **/
public class Subject560 {

    /**
     * 数组 pre[i] 下标0到i的和 当pre[j] - pre[i] = k时符合条件
     * 用map来记录和，为了避免判断j大于i，则map从前往后遍历
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int[] pre = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if(i == 0) {
                pre[i] = nums[i];
            } else {
                pre[i] = nums[i] + pre[i-1];
            }
        }
        int result = 0;
        //key时pre[i]的值，value是次数
        Map<Integer,Integer> preMap = new HashMap<>();
        // 当差值为0时，自身必然算一个
        preMap.put(0,1);
        for (int i = 0; i < nums.length; i++) {
            int sum = pre[i] - k;
            Integer count = preMap.getOrDefault(sum, 0);
            result = result + count;
            preMap.put(pre[i], preMap.getOrDefault(pre[i],0)+1);

        }
        return result;
    }


    @Test
    public void test() {
        System.out.println(subarraySum(new int[]{-1,-1,1},0));

    }

}
