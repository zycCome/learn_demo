package com.zyc.learn_demo.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试compute
 *
 * @author zhuyc
 * @date 2021/7/8 16:31
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        ConcurrentHashMap<String,Long> map = new ConcurrentHashMap<>();
        Long a = map.compute("a",(k,v) -> {
            System.out.println(k);
            if(v == null) {
                v = 0L;
            }
            return ++v;

        });
        System.out.println("a:" + a);
        Long a1 = map.compute("a",(k,v) -> {
            System.out.println(k);
            if(v == null) {
                v = 0L;
            }
            return ++v;

        });
        System.out.println("a1:" + a1);
    }

    /**
     * 合并两个已排序的整数数组
     * 此方法的目的是将两个已排序的整数数组合并成一个单一的已排序数组
     * 它通过比较两个数组中的元素，并以升序将它们放入新数组中来实现
     *
     * @param a 第一个已排序的整数数组
     * @param b 第二个已排序的整数数组
     * @return 返回一个包含两个输入数组所有元素的新已排序数组
     */
    public static int[] merge(int[] a,int[] b) {
        // 创建一个新数组，长度为两个输入数组长度之和
        int[] result = new int[a.length + b.length];
        // 初始化三个指针，分别指向数组a、b和结果数组result的当前元素
        int i = 0,j = 0,k = 0;

        // 遍历数组a和b，直到其中一个数组的所有元素都被合并
        while (i < a.length && j < b.length) {
            if(a[i] < b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
            // 比较数组a和b的当前元素，将较小的元素添加到结果数组中
        }

        // 如果数组a中还有剩余元素，将它们直接添加到结果数组中
        while (i < a.length) {
            result[k++] = a[i++];
        }

        // 如果数组b中还有剩余元素，将它们直接添加到结果数组中
        while (j < b.length) {
            result[k++] = b[j++];
        }

        // 返回合并后的数组
        return result;
    }
}
