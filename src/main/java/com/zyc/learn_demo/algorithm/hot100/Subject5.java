package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * 最长回文子串
 *
 * @author zhuyc
 * @date 2022/10/01 22:03
 **/
public class Subject5 {

    /**
     * 动态规划
     * 二维数组：array[x][y] 表示字符串子串x-y是否是回文(只有对角线的一半)
     *
     * 状态转移方程式
     * array[a][b]
     * 1. a==b,true
     * 2. b - a == 1, charAt(a) == charAt(b)
     * 3. b -a ==  2, charAt(a) == chart(b)
     * 4. b -a > 2 , array[a+1][b-1] && charAt(a) == chart(b)
     *
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if(s.length() <= 1) {
            return s;
        }
        int maxLength = 1;
        int[] result = {0,0};
        boolean[][] flags = new boolean[s.length()][s.length()];
        // i 表示子串的结尾
        for (int i = 0; i < s.length(); i++) {
            flags[i][i] = true;
            // j是子串的开始，从大到小
            for(int j = i -1;j >= 0; j--) {
                if(i -j <= 2 && s.charAt(i) == s.charAt(j)) {
                    flags[j][i] = true;
                } else if (flags[j+1][i-1] &&  s.charAt(i) == s.charAt(j)){
                    flags[j][i] = true;
                }
                if(flags[j][i] && i-j+1 > maxLength) {
                    maxLength =  i-j+1;
                    result[0] = j;
                    result[1] = i;
                }
            }
        }
        return s.substring(result[0],result[1]+1);
    }

    @Test
    public void test() {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("cbbd"));
    }
}
