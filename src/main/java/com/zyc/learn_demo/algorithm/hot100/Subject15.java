package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author zilu
 * @Date 2022/10/9 12:40 PM
 * @Version 1.0.0
 **/
public class Subject15 {


    /**
     * 1. 遍历一遍，统计每个值的数量。key是值，value是数量
     * 2. key 排序,三个数 后面的值 >= 前面的值
     * 3. 前两个数 枚举，第三个数map直接查
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length <3) {
            return result;
        }
        Map<Integer,Integer> numCount = new HashMap<>();
        for (int num : nums) {
            numCount.put(num,numCount.getOrDefault(num,0)+1);
        }

        List<Integer> numList = numCount.keySet().stream().sorted(Comparator.comparingInt(i -> i)).collect(Collectors.toList());
        for (int i = 0; i < numList.size(); i++) {
            // 取第一个数，必然能取到
            Integer value = numList.get(i);
            Integer valueCount = numCount.get(value);
            numCount.put(value,valueCount-1);
            for (int j = i; j < numList.size(); j++) {
                // j不能往小了取，否则会重复
                Integer value2 = numList.get(j);
                Integer value2Count = numCount.get(value2);
                if(value2Count == 0) {
                    // 结束，下一个
                    continue;
                } else  {
                    numCount.put(value2,value2Count-1);
                    // 第三个用map找，不允许小于j
                    int needNum = 0 - value - value2;
                    if(needNum < value2) {
                    } else if(numCount.getOrDefault(needNum,0) > 0){
                        List<Integer> answer = new ArrayList<>();
                        answer.add(value);
                        answer.add(value2);
                        answer.add(needNum);
                        result.add(answer);
                    }

                    numCount.put(value2,value2Count);
                }


            }
            // 还原
            numCount.put(value,valueCount);
        }
        return result;
    }

    @Test
    public void test() {
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> lists = threeSum(nums);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
        System.out.println("------");
        int[] nums2 = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> lists2 = threeSum2(nums2);
        for (List<Integer> list : lists2) {
            System.out.println(list);
        }
    }


    /**
     * 局部双指针
     * 1 先按nums排序，排序的目的是为了去掉排列的场景
     * 2 遍历第一个数，跳过一样的（因为首个数是n的所有组合只需要出现一次）
     * 3 双指针遍历时也需要跳过一样的数（去重）
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length <3) {
            return result;
        }
        Arrays.sort(nums);
        int pre1 = nums[0] -1;
        for (int i = 0; i < nums.length; i++) {
            if(pre1 == nums[i]) {
                continue;
            }
            pre1 = nums[i];
            // 后两个数需要用双指针
            int low = i+1;
            int hign = nums.length -1;
            int sum = 0 - nums[i];
            while(low < hign) {
                int v = nums[low] + nums[hign];
                if(v == sum) {
                    List<Integer> answer = new ArrayList<>();
                    answer.add(nums[i]);
                    answer.add(nums[low]);
                    answer.add(nums[hign]);
                    result.add(answer);
                    // 随便左移/右移都可以
                    // 移动左（低）指针
                    int aim = low + 1;
                    while(aim < hign && nums[aim] == nums[low]) {
                        aim = aim+1;
                    }
                    low = aim;
                } else if (v < sum) {
                    // 移动左（低）指针
                    int aim = low + 1;
                    while(aim < hign && nums[aim] == nums[low]) {
                        aim = aim+1;
                    }
                    low = aim;
                } else {
                    // 移动右（高）指针
                    int aim = hign - 1;
                    while(aim > low && nums[aim] == nums[hign]) {
                        aim = aim-1;
                    }
                    hign = aim;
                }
            }

        }

        return result;
    }
}
