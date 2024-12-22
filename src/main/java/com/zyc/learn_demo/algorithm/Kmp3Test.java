package com.zyc.learn_demo.algorithm;

import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2023/11/30 14:25
 * @Version 1.0.0
 **/
public class Kmp3Test {

    @Test
    public void test() {
        System.out.println(find("abcabaabaabcacb", "abaabcac"));

    }

    public int find(String main,String pattern) {
        int[] prefix =  buildPrefix(pattern);
        movePrefix(prefix);
        // i是主串下标，j是模式串下标。i不会回退
        int i=0;
        int j = 0;
        int result = -1;
        while(i < main.length()) {
            // 如果下一个就是匹配时
            if((j == pattern.length() -2) && (main.charAt(i+1) == pattern.charAt(j+1))) {
                // 计算下标
                System.out.println("find:"+(i-j));
                result = i-j;

                // 找到了.继续找
                j = prefix[j];
            }
            if(main.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
            } else {
                j = prefix[j];
                if(j == -1){
                    j=0;
                    i++;
                }
            }
        }
        return result;
    }

    /**
     * 右移动一位。方便后续处理
     *
     * @param prefix
     */
    private void movePrefix(int[] prefix) {
        for (int i = prefix.length-1; i > 0; i--) {
            prefix[i] = prefix[i-1];
        }
        prefix[0] = -1;
    }

    /**
     * prefix数组定义
     * prefix[j]=i.标识从0-j的字符串的最长相同前后缀长度
     * prefix[0]必然为0
     * @param pattern
     * @return
     */
    private int[] buildPrefix(String pattern) {
        int[] prefix = new int[pattern.length()];
        int i = 1;
        // 前一个下标的最长前后缀真子串长度
        int len = 0;
        while(i < pattern.length()) {
            if(pattern.charAt(i) == pattern.charAt(len)) {
                prefix[i] = ++len;
                i++;
            } else {
                if(len == 0) {
                    prefix[i] = 0;
                    i++;
                } else {
                    len = prefix[len -1];
                }

            }
        }
        return prefix;
    }

}
