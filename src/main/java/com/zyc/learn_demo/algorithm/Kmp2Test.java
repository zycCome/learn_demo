package com.zyc.learn_demo.algorithm;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/10/04 09:47
 **/
public class Kmp2Test {

    /**
     * 字符串匹配
     * 再写一次KMP
     *
     * 核心思想：
     * 1. i j两个指针，不会滚i指针
     * 2. 构建next失效数组。下标表示模式串哪个下标匹配失败。value表示j跳转的下标。下次i和跳转后的j下标匹配。
     * 3. next数组的含义：当前下标前面部分（不包含当前下标），最长公共前后缀的长度
     *
     *
     * @param main
     * @param pattern
     */
    public int find(String main,String pattern) {
        if(pattern.length() > main.length()) {
            return -1;
        }

        int[] next = build(pattern);
        int j = 0;
        for (int i = 0; i < main.length() ; i++) {
            while(true) {
                if(main.charAt(i) == pattern.charAt(j)) {
                    j++;
                    if(j == pattern.length()) {
                        return i - pattern.length() + 1;
                    }
                    break;
                } else {
                    // 匹配失败找j跳转的位置
                    if(j == 0) {
                        // 模式串第一位不匹配，递增i
                        break;
                    }
                    j = next[j];
                }
            }
        }
        return -1;
    }

    /**
     * 构建next数组
     * @param pattern
     * @return
     */
    private int[] build(String pattern) {
        int[] next = new int[pattern.length()];
        // 根据next数组的定义+长度小于2时，没有最长公共前后缀，因此next 0 1两位不用处理，固定0
        for (int i = 2; i < pattern.length() ; i++) {
            int compareIndex = i-1;
            while(compareIndex >= 0) {
                // 参与比较的两个位置，i-1这个位置是不变的！！
                if(pattern.charAt(i-1) == pattern.charAt(next[compareIndex])) {
                    // 加长一位
                    next[i] = next[compareIndex] +1;
                    break;
                } else {
                    if(next[compareIndex] == 0) {
                        // 和第一位不匹配，处理下一位
                        break;
                    }
                    // 缩短代匹配部分
                    compareIndex = next[compareIndex];

                }
            }

        }

        return next;
    }

    @Test
    public void test() {
        System.out.println(find("abcabaabaabcacb", "abaabcac"));

    }

}



