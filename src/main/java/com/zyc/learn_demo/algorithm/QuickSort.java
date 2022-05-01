package com.zyc.learn_demo.algorithm;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/04/07 15:19
 **/
public class QuickSort {


    /**
     * 思路：
     * 找到一个基准值，将该值插入到正确位置，然后对两边排序
     *
     * @param array
     * @return
     */
    public void sort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = sort2(array, start, end);
        sort(array, start, mid - 1);
        sort(array, mid + 1, end);


    }

    /**
     * 指针交换法
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    private int sort2(int[] array, int start, int end) {

        int temp = array[start];
        int low = start;
        int high = end;
        while (high > low) {
            while (high > low && array[high] >= temp) {
                high--;
            }

            while (high > low && array[low] < temp) {
                low++;
            }

            // 交换指针
            int v = array[low];
            array[low] = array[high];
            array[high] = v;
        }

        return low;

    }

    @Test
    public void test() {
        int arr[] = {23, 3,4,8,55,4, 7, 9, 6, 4, 5, 56, 2,42, 34, 2,55, 44};
        sort(arr, 0, arr.length - 1);

        for (int a : arr)  //遍历数组进行打印
            System.out.print(a + "  ");
        System.out.println();
    }

}
