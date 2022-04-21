package com.zyc.learn_demo.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/04/06 15:48
 **/
public class KmpTest {

    /**
     * 寻找匹配串在主串中的第一个完整匹配的下标
     *
     * @param haystack
     * @param needle
     * @return -1 表示找不到
     */
    public int find(String haystack, String needle) {
        int result = -1;
        Assert.assertNotNull(haystack);
        Assert.assertNotNull(needle);
        if (needle.length() > haystack.length()) {
            return result;
        }

        // 构建next数组.next数组：分别代表长度为 下标 长度的字符串中的最长公共前后缀长度
        int[] next = build(needle);

        // 对应模式串待匹配的下标
        int i = 0;
        int j = 0;
        while (j < needle.length() && i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
                i++;
            } else {
                if (j == 0) {
                    //和模式串第一位不匹配时，增加i
                    i++;
                } else {
                    // 其他不匹配时，j回溯，i不变
                    j = next[j];
                }
            }
        }

        if (j == needle.length()) {
            result = i - needle.length();
        }
        return result;

    }

    private int[] build(String patter) {
        int[] next = new int[patter.length()];
        if (patter.length() < 2) {
            return next;
        }
        // 计算长度为i时的next数组
        for (int i = 2; i < patter.length(); i++) {
            int now = i - 1;
            while (true) {
                // 如果
                if (patter.charAt(i-1) == patter.charAt(next[now])) {
                    next[i] = next[now] + 1;
                    break;
                } else {
                    if (now == 0) {
                        break;
                    }
                    now = next[now ];
                }
            }


        }
        return next;
    }


    @Test
    public void test() {
        System.out.println(find("abcabaabaabcacb", "abaabcac"));

    }


    /**
     * 求出一个字符数组的next数组
     * @param t 字符数组
     * @return next数组
     */
    public static int[] getNextArray(char[] t) {
        int[] next = new int[t.length];
        next[0] = -1;
        next[1] = 0;
        int k;
        for (int j = 2; j < t.length; j++) {
            k=next[j-1];
            while (k!=-1) {
                if (t[j - 1] == t[k]) {
                    next[j] = k + 1;
                    break;
                }
                else {
                    k = next[k];
                }
                next[j] = 0;  //当k==-1而跳出循环时，next[j] = 0，否则next[j]会在break之前被赋值
            }
        }
        return next;
    }

    /**
     * 对主串s和模式串t进行KMP模式匹配
     * @param s 主串
     * @param t 模式串
     * @return 若匹配成功，返回t在s中的位置（第一个相同字符对应的位置），若匹配失败，返回-1
     */
    public static int kmpMatch(String s, String t){
        char[] s_arr = s.toCharArray();
        char[] t_arr = t.toCharArray();
        int[] next = getNextArray(t_arr);
        int i = 0, j = 0;
        while (i<s_arr.length && j<t_arr.length){
            if(j == -1 || s_arr[i]==t_arr[j]){
                i++;
                j++;
            }
            else
                j = next[j];
        }
        if(j == t_arr.length)
            return i-j;
        else
            return -1;
    }

    public static void main(String[] args) {
        System.out.println(kmpMatch("abcabaabaabcacb", "abaabcac"));
    }

}
