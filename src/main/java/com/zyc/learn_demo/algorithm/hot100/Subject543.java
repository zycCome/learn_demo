package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/10/03 19:13
 **/
public class Subject543 {

    int max = 0;

    // 计算每个节点的左右两边最大高度之和
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return max;
    }

    // 返回当前节点的最大高度
    public int dfs(TreeNode node) {
        if(node == null) {
            return -1;
        }
        int right = dfs(node.right) + 1;
        int left = dfs(node.left) + 1;
        int value = right + left;
        max = Math.max(value,max);
        return  Math.max(right,left);
    }

    @Test
    public void test() {
        TreeNode convert = TreeNode.convert(new int[]{1, 2, 3, 4, 5});
        System.out.println(diameterOfBinaryTree(convert));
    }

}
