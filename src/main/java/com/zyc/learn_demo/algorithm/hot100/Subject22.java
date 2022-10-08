package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuyc
 * @date 2022/10/03 09:46
 **/
public class Subject22 {

    /**
     * 回溯法
     * 截止条件：
     *  1. 右边的括号大于左边的括号
     *  2. 左边的括号用完了
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        generate(0,0,result,"",n);
        return result;
    }

    private void generate(int left, int right, ArrayList<String> result, String s, int n) {
        if(left > n) {
            return;
        }
        if(right > left) {
            return;
        }
        if(left == n && left == right) {
            result.add(s);
            return;
        }
        // 只有两种可能，加左边或者加右边
        generate(left+1,right,result,s+"(",n);
        generate(left,right+1,result,s+")",n);

    }

    @Test
    public void test() {
        List<String> strings = generateParenthesis(3);
        strings.forEach(System.out::println);
    }

}
