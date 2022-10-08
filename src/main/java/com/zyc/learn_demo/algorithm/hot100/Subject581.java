package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/10/03 15:34
 **/
public class Subject581 {

    /**
     * 两次遍历，将数组分成三段abc ，ac段是有序的，且c中的最小值大于ab最大值，a的最大值小于bc最小值
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        // 找ab的最大值，从左往右遍历，记录max，如果nums[i] < max 则记录最后一个i，最后一个i就是b的end
        int max = nums[0];
        int end = -1;


        int min = nums[nums.length - 1];
        int start = -1;
        for (int i = 0; i < nums.length; i++) {
            if(max > nums[i]) {
                end = i;
            } else {
                max = nums[i];
            }

            // 肯定不属于a
            int j = nums.length -1 -i;
            if(nums[j] > min) {
                start = j;
            } else {
                min = nums[j];
            }
        }

        if(end == -1) {
            return 0;
        }
        return end-start+1;
    }

    @Test
    public void test() {
        System.out.println(findUnsortedSubarray(new int[] {2,6,4,8,10,9,15}));
    }


    @Test
    public void test2() {
        System.out.println(findUnsortedSubarray(new int[] {1,2,3,4}));
    }

    @Test
    public void test3() {
        System.out.println(findUnsortedSubarray(new int[] {1,3,2,2,2}));
    }
}
