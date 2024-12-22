package com.zyc.learn_demo.algorithm;

import org.junit.Test;

/**
 * @author zyc66
 * @date 2024/12/12 14:43
 **/
public class Kmp4Test {


    @Test
    public void test() {
        System.out.println(find("abcabaabaabcacb", "abaabcac"));

    }

    public int find(String main,String pattern) {
        int[] next = findNext(pattern);
        // i 是主串上的下标
        int i = 0,j = 0;

        while(j < pattern.length()) {
            if(main.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if(j == 0) {
                    i++;
                } else {
                    j = next[j-1];
                }
            }
        }
        if(j == pattern.length()) {
            return i- pattern.length();
        }
        return -1;
    }

    /**
     * next 数组的意思：
     * next[i] 表示 0 -i （左闭右闭）字符串的最长相等前后缀。同时next[i] == len 表示这个字符串第一个不匹配的位置;
     * @param pattern
     * @return
     */
    private int[] findNext(String pattern) {
        int[] next = new int[pattern.length()];
        int i = 1;
        int prefixLen = 0; // 默认就是next[i-1] == 0
        while(i < pattern.length()) {
            if(pattern.charAt(prefixLen) == pattern.charAt(i)) {
                prefixLen += 1;
                next[i++] = prefixLen;
            } else {
                if(prefixLen == 0) {
                    next[i++] = prefixLen;
                } else {
                    prefixLen = next[prefixLen - 1];
                }
            }
        }
        return next;
    }
}
