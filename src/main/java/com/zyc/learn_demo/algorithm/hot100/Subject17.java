package com.zyc.learn_demo.algorithm.hot100;

import cn.hutool.core.collection.ListUtil;
import org.junit.Test;

import java.util.*;

/**
 * @author zhuyc
 * @date 2022/10/04 06:26
 **/
public class Subject17 {

    public List<String> letterCombinations(String digits) {
        if(digits.length() == 0) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        result.add("");
        for (char c : digits.toCharArray()) {
            List<Character> chars = map.get(c);
            List<String> temp = new ArrayList<>();
            for (Character aChar : chars) {
                for (String s : result) {
                    temp.add(s+aChar);
                }
            }
            result = temp;
        }
        return result;
    }

    Map<Character,List<Character>> map = new HashMap() {
        {
            this.put('2',new ArrayList<>(Arrays.asList('a','b','c')));
            this.put('3',new ArrayList<>(Arrays.asList('d','e','f')));
            this.put('4',new ArrayList<>(Arrays.asList('g','h','i')));
            this.put('5',new ArrayList<>(Arrays.asList('j','k','l')));
            this.put('6',new ArrayList<>(Arrays.asList('m','n','o')));
            this.put('7',new ArrayList<>(Arrays.asList('p','q','r','s')));
            this.put('8',new ArrayList<>(Arrays.asList('t','u','v')));
            this.put('9',new ArrayList<>(Arrays.asList('w','x','y','z')));
        }
    };

    @Test
    public void test() {
        List<String> strings = letterCombinations("23");
        strings.forEach(System.out::println);

    }


}
