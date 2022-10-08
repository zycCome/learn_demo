package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * 寻找两个正序数组的中位数
 *
 * @author zhuyc
 * @date 2022/10/01 13:45
 **/
public class Subject4 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums3 = new int[nums1.length + nums2.length];
        int i =0,j =0,k = 0;
        while(i < nums1.length && j < nums2.length) {
            if(nums1[i] <= nums2[j]) {
                nums3[k++] = nums1[i++];
            } else {
                nums3[k++] = nums2[j++];
            }
        }
        while(i < nums1.length) {
            nums3[k++] = nums1[i++];
        }
        while (j < nums2.length) {
            nums3[k++] = nums2[j++];
        }

        if(nums3.length % 2 == 0) {
            // 偶数
            return (double) (nums3[nums3.length/2] + nums3[nums3.length /2 -1])/2;
        } else {
            // 奇数
            return nums3[nums3.length/2];
        }
    }


    /**
     * 二分查找法
     * 找两个数组第k小的数：
     *  两个数组各取 K/2 然后比较。小的那个数组，startIndex取 K/2+1
     *
     * 不断缩小k，直到某个数组下标越界或者k=1
     *
     * 寻找k时，
     * 如A[k/2] < B[k/2] 则 第k小的数组 肯定不在A[0-k/2]（无论k的奇偶），因为小于等于A[k/2]的肯定没有k个了
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int totalSize = nums1.length + nums2.length;
        if(totalSize % 2 == 1) {
            return findK(totalSize /2 + 1,nums1,0,nums1.length-1,nums2,0,nums2.length-1);
        } else {
            int pre = findK(totalSize /2 + 1,nums1,0,nums1.length-1,nums2,0,nums2.length-1);
            int after = findK(totalSize /2 ,nums1,0,nums1.length-1,nums2,0,nums2.length-1);
            return (double)(pre + after) / 2;
        }
    }

    /**
     * 两个寻找第k小的数
     * @param k 注意数组下标从0开始，因此需要 k-1
     * @param nums1
     * @param startIndex1
     * @param endIndex1
     * @param nums2
     * @param startIndex2
     * @param endIndex2
     * @return
     */
    private int findK(int k, int[] nums1, int startIndex1, int endIndex1, int[] nums2, int startIndex2, int endIndex2) {
        if(startIndex1 > endIndex1) {
            // 不可能在数组1中了
            return nums2[startIndex2 + k -1];
        }
        if(startIndex2 > endIndex2) {
            // 不可能在数组1中了
            return nums1[startIndex1 + k -1];
        }
        if(k == 1) {
            return Math.min(nums2[startIndex2],nums1[startIndex1]);
        }
        int index = k / 2;
        int nums1Index = Math.min(endIndex1,startIndex1+index-1);
        int nums2Index = Math.min(endIndex2,startIndex2+index-1);
        if(nums1[nums1Index] < nums2[nums2Index]) {
            // 缩小k,剪掉排除的元素
            // 获取实际k缩小的值，既排除了几个元素
            int actualK = nums1Index - startIndex1 + 1;
            return findK(k-actualK,nums1,startIndex1+actualK,endIndex1,nums2,startIndex2,endIndex2);
        } else {
            // 大于等于（因为等于的话，对结果没影响，取哪个数组的都一样）
            int actualK = nums2Index - startIndex2 + 1;
            return findK(k-actualK,nums1,startIndex1,endIndex1,nums2,startIndex2+actualK,endIndex2);
        }

    }

    @Test
    public void test() {
//        int[] nums1 = {1};
//        int[] nums2 = {2,3,4,5,6};
//        System.out.println(findMedianSortedArrays2(nums1,nums2));

//        int[] nums1 = {1,3};
//        int[] nums2 = {2};
//        System.out.println(findMedianSortedArrays2(nums1,nums2));

        int[] nums1 = {0,0};
        int[] nums2 = {0,0};
        System.out.println(findMedianSortedArrays2(nums1,nums2));

    }

}
