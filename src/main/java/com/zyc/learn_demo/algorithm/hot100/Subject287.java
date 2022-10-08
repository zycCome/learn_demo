package com.zyc.learn_demo.algorithm.hot100;

/**
 * @author zhuyc
 * @date 2022/10/03 16:37
 **/
public class Subject287 {

    /**
     * 将数组看成链表，寻找循环链表
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        do {
            // 下一个下标
            slow = nums[slow];
            fast = nums[nums[fast]];
            // 相遇的定义：下标一样
            if(slow == fast) {
                // 第一次相遇了,以一样的速度，再次相遇时，就是重复的点
                slow = 0;
                while(true) {
                    if(slow == fast) {
                        return nums[slow];
                    }
                    slow = nums[slow];
                    fast = nums[fast];
                }

            }
        } while (true);
    }

}
