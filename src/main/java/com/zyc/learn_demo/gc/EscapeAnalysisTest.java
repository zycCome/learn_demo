package com.zyc.learn_demo.gc;

/**
 * @author zyc66
 * @date 2024/12/23 09:36
 **/
public class EscapeAnalysisTest {
    public static void main(String[] args) {
        int n = 100000000;
        long start = System.currentTimeMillis();
        EscapeAnalysis escapeAnalysis = new EscapeAnalysis();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // noEscape()不会发生逃逸
//            escapeAnalysis.noEscape();
            sum += escapeAnalysis.noEscapeInteger();

        }
        System.out.println(sum);
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
