package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 找到字符串中所有字母异位词
 * @Author zilu
 * @Date 2022/11/10 12:53 PM
 * @Version 1.0.0
 **/
public class Subject438 {


    /**
     * 找到字符串中所有字母异位词
     * 思路：
     * 1. 整体采用滑动窗口避免重复计算
     * 2. 比较不是用数组比较，而是用一个差值diff比较，
     *  2.1 初始化一个统计s中所有字符出现次数的数组count[],count大小固定26（所有小些字母），遍历s统计count[字符 - 'a']++
     *  2.2 初始化：遍历 p[0,p.length]中的，字符出现，则在count数组对应位置-1。如果某个字符和s出现次数一样，则字符对应位置为0
     *  2.3 diff的定义：count数组中对应值中不为0的下标个数（也就是字符数量有偏差的字符类型数量）
     *  2.2 diff为0时，表示是异位词
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if(s.length() < p.length()) {
            return result;
        }
        int[] count = new int[26];

        for (int i = 0; i < p.length(); i++) {
            count[p.charAt(i)- 'a']++;
            // s中出现则抵消
            count[s.charAt(i) - 'a']--;
        }

        // 统计值不为0的字符数
        int diff = 0;
        for (int i = 0; i < count.length; i++) {
            if(count[i] != 0) {
                diff++;
            }
        }

        if(diff == 0) {
            result.add(0);
        }

        // 开始滑动窗口，右边加一位，左边减一位
        // 移动次数 = 比较次数 -1。比较次数 = s.length - p.length + 1.既s.length - p.length次
        for (int i = 0; i < (s.length() - p.length()); i++) {
            if(count[s.charAt(i) - 'a'] == -1) {
                diff--;
            }
            if(count[s.charAt(i) - 'a'] == 0) {
                diff++;
            }
            // 出现-，消失需要+
            count[s.charAt(i) - 'a']++;


            int rightIndex = i + p.length();
            if(count[s.charAt(rightIndex) - 'a'] == 1) {
                diff--;
            }
            if(count[s.charAt(rightIndex) - 'a'] == 0) {
                diff++;
            }
            // 出现-
            count[s.charAt(rightIndex) - 'a']--;

            if(diff == 0) {
                // i是窗口删除的下标
                result.add(i+1);
            }

        }
        return result;


    }

    @Test
    public void test1() {
        System.out.println(findAnagrams("baa","aa"));

    }

    public static List<Integer> findAnagrams2(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s.length() < p.length()) {
            return list;
        }
        char[] sCharArray = s.toCharArray();
        char[] pCharArray = p.toCharArray();
        //统计需要的字符及其数量
        int[] need = new int[26];
        for (char c : pCharArray) {
            need[c - 'a']++;
        }

        //移动窗口
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            char rightChar = sCharArray[right];
            --need[rightChar - 'a'];
            while (need[rightChar - 'a'] < 0) {  //移动左边界直到这个负数变成非负数    ++left 要放在最后
                char leftChar = sCharArray[left];
                ++need[leftChar - 'a'];
                ++left;
            }
            //如果窗口长度 == s.length()，那么就是可行解
            if (right - left + 1 == p.length()) {
                list.add(left);
            }
            right++;  //移动右边界
        }
        return list;
    }

}
