package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.TreeMap;

/**
 * 跳跃游戏
 *
 * @author zhuyc
 * @date 2022/10/02 20:49
 **/
public class Subject55 {

    /**
     * 建立一个数组，从后往前遍历，数组中的值表示能否达到最后一个元素
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if(nums.length == 0) {
            return true;
        }
        int latestIndex = nums.length - 1;
        boolean[] canJump = new boolean[nums.length];
        for (int i = latestIndex; i >= 0; i--) {
            // 判断i下标能否达到末尾，如果i+nums[i] >= lastIndex 或者 i+nums[i]中的某个下标为true
            if(i + nums[i] >= latestIndex) {
                canJump[i] = true;
            } else {
                for (int j = 1; j <= nums[i]; j++) {
                    if(canJump[i+j]) {
                        canJump[i] = true;
                    }
                }
            }
        }
        return canJump[0];
    }


    /**
     * 如果一个位子可以起跳到n，那么n之前的位置都可以作为起跳点，不断更新能跳到的最大位置
     * @param nums
     * @return
     */
    public boolean canJump2(int[] nums) {
        if(nums.length == 0) {
            return true;
        }
        int max = nums[0];
        int i = 0;
        while (i <= max && i < nums.length ) {
            max = Math.max(i+nums[i],max);
            i++;
        }
        return max >= nums.length - 1;
    }

    @Test
    public void test() {
        int[] array = {2,3,1,1,4};
        System.out.println(canJump2(array));
    }

    @Test
    public void test2() {
        int[] array = {3,2,1,0,4};
        System.out.println(canJump2(array));
    }

    @Test
    public void test3() {
        int[] array = {1,2,3};
        System.out.println(canJump2(array));
    }

}
