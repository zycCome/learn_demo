package com.zyc.learn_demo.algorithm;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * @Description
 * @Author zilu
 * @Date 2022/9/9 12:41 PM
 * @Version 1.0.0
 **/
public class BmTest {


    /**
     * 思路：
     * 1 构造坏字符数组
     * 2 构造好后缀的两个数组
     * 3 从后往前匹配如果不符合，则查找 max(a,b)中的值并右移pattern
     * @param pattern
     * @param str
     * @return 起始位置
     */
    public int search(String pattern,String str) {
        if(StrUtil.isEmpty(pattern) || StrUtil.isEmpty(str) || pattern.length() > str.length()) {
            return -1;
        }

        int[] bc = buildBC(pattern);
        // 该数组的下标表示后缀子串的长度，value表示模式串中最右一个匹配的字串的位置
        int[] suffix = new int[pattern.length()];
        // 当存在suffix[k] = 0时，则prefix[k] = true;
        // 该数组的下标表示后缀子串的长度，值表示是否存在对应的前缀字串
        boolean[] prefix = new boolean[pattern.length()];
        buildGS(suffix,prefix,pattern);

        // i是patter起点在str中的下标
        for(int i = 0; i <= str.length() - pattern.length();i++) {
            // 模式串中待匹配的下标
            int j = pattern.length() - 1;
            while(j >= 0 &&  pattern.charAt(j) == str.charAt(i+j)) {
                j--;
            }
            if(j < 0){
                // 之前都匹配了
                return i;
            }
            // 有不匹配的，需要后移.坏字符的位置
            int bcSize = j - bc[str.charAt(i+j)];
            int gsSize = 0;
            if(j < pattern.length() - 1) {
                // 如果有好后缀,没有后缀也就不可能出现pattern左移的问题
                gsSize = gsSize(suffix,prefix,j ,pattern.length());
            }

            i = i + Math.max(bcSize,gsSize);
        }

        return -1;
    }

    private int gsSize(int[] suffix, boolean[] prefix, int j, int patternLength) {
        int matchLength = patternLength -j -1;
        // 好后缀不需要前缀匹配
        if(suffix[matchLength] > -1) {
            return j - suffix[matchLength] + 1;
        }

        // 好后缀必须匹配前缀字符
        for(int k = matchLength - 1;k>0;k--) {
            if(prefix[k]) {
                return patternLength - k ;
            }
        }
        return patternLength;
    }

    /**
     * 构建好后缀
     * @param suffix
     * @param prefix
     * @param pattern
     */
    private void buildGS(int[] suffix, boolean[] prefix, String pattern) {
        // 不能是0
        Arrays.fill(suffix, -1);
        // 后缀字串所匹配字串p的下标的截止位置下标是pattern-2
        int length = pattern.length();
        for(int i= 0;i < length - 1 ; i++) {
            // p和后缀字符都是从后往前匹配,j表示当前p串中待匹配的下标
            int j = i;
            // k表示后缀字串的长度
            int k = 0;
            while(j > -1 && pattern.charAt(j) == pattern.charAt(length - 1 - k)) {
                k++;
                suffix[k] = j;
                j--;
            }
            // -1说明0到i之间的都匹配了
            if(j == -1) {
                prefix[k] = true;
            }

        }
    }

    /**
     * 构造坏字符数组，记录pattern中每个字符最右的位置。数组大小和字符串范围有关
     * @param pattern
     * @return
     */
    private int[] buildBC(String pattern) {
        int[] result = new int[65535];
        for (int i : result) {
            // 如果没有字符串为-1
            result[i] = -1;
        }
        // 右边的位置覆盖左边的
        for (int i = 0; i < pattern.length(); i++) {
            result[pattern.charAt(i)] = i;
        }
        return result;
    }

}
