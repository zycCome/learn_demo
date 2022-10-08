package com.zyc.learn_demo.algorithm.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuyc
 * @date 2022/10/03 11:36
 **/
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    // 数组转节点
    public static TreeNode convert(int[] array) {
        if (array.length == 0) {
            return null;
        }
        Map<Integer, TreeNode> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                continue;
            }
            TreeNode node = new TreeNode(array[i]);
            map.put(i, node);

            // 获取父节点
            int parentIndex = (i - 1) / 2;
            if (parentIndex >= 0 && parentIndex != i) {
                if ((i - 1) % 2 == 0) {
                    map.get(parentIndex).setLeft(node);
                } else {
                    map.get(parentIndex).setRight(node);
                }
            }
        }
        return map.get(0);
    }

    public static TreeNode findOne(TreeNode root,int val) {
        if(root == null) {
            return null;
        }
        if(root.val == val) {
            return root;
        }
        TreeNode one = findOne(root.left, val);

        if(one != null) {
            return one;
        }
        one = findOne(root.right, val);
        if(one != null) {
            return one;
        }
        return null;
    }
}
