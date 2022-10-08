package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 无重复字符的最长子串
 *
 * @author zhuyc
 * @date 2022/10/01 13:45
 **/
public class Subject3 {


    /**
     * 思路：
     * 动态规划思想，数组T，T(N) 表示以下标N结尾的字符串的最长无重复子串的最大长度
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s == null) {
            return 0;
        }
        if(s.length() < 1) {
            return s.length();
        }
        // 累计的最大长度
        int maxLength = 1;
        // 下标i前一位的最大子串长度
        int preMaxLength = 1;
        HashSet<Character> set = new HashSet<>();
        char[] chars = s.toCharArray();
        set.add(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            while (set.contains(chars[i])) {
               set.remove(chars[i-preMaxLength]);
               preMaxLength--;
            }
            preMaxLength = preMaxLength+1;
            maxLength = Math.max(maxLength,preMaxLength);
            set.add(chars[i]);
        }
        return maxLength;

    }

    /**
     * 采用滑动窗口算法
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        if(s.length() == 0) {
            return 0;
        }
        // 之前窗口各个字符的下标
        Map<Character,Integer> map = new HashMap<>();
        int max = 1;
        char[] chars = s.toCharArray();
        int windowLeftIndex = 0;
        // i表示窗口的右边
        for (int i = 0; i < chars.length; i++) {
            if(map.containsKey(chars[i])) {
                Integer index = map.get(chars[i]);
                // 窗口从上一次字符出现位置的右边开始
                // 同时需要避免窗口向左回滚的问题，因为map中包含窗口左边的位置。
                windowLeftIndex = Math.max(windowLeftIndex,index+1);
            }
            map.put(chars[i],i);
            max = Math.max(max,i - windowLeftIndex + 1);
        }
        return max;

    }


    @Test
    public void test() {
        System.out.println(lengthOfLongestSubstring2("abba"));
        System.out.println(lengthOfLongestSubstring2("abcabcbb"));
        System.out.println(lengthOfLongestSubstring2("bbbbb"));
        System.out.println(lengthOfLongestSubstring2("pwwkew"));
    }
}
