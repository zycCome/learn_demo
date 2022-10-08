package com.zyc.learn_demo.algorithm.hot100;

import java.util.Stack;

/**
 *
 * 有效的括号
 * @author zhuyc
 * @date 2022/10/04 07:33
 **/
public class Subject20 {

    /**
     * 通过栈
     * 遍历字符如果是右边字符则放入，左边字符串则出栈，直到找到匹配的右字符或者栈为空
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if(c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if(c == ')') {
                if (!stack.empty()) {
                    Character pop = stack.pop();
                    if(pop == '(') {
                        continue;
                    }
                }
                return false;
            } else if(c == '}') {
                if (!stack.empty()) {
                    Character pop = stack.pop();
                    if(pop == '{') {
                        continue;
                    }
                }
                return false;
            } else if(c == ']') {
                if (!stack.empty()) {
                    Character pop = stack.pop();
                    if(pop == '[') {
                        continue;
                    }
                }
                return false;
            }

        }
        return stack.empty();
    }

}
