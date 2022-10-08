package com.zyc.learn_demo.algorithm.hot100;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhuyc
 * @date 2022/10/03 08:09
 **/
public class Subject337 {


    /**
     * 回溯法
     * 拿到一个节点后，只有两种情况，偷或不偷
     */
    public int rob(TreeNode root) {
        return choose(0,new HashSet<>(),root);
    }


    private int choose(int value, Set<TreeNode> excludes, TreeNode node) {
        if(node == null) {
            return value;
        }
        int value1 = value;
        // 不偷
        value1 = choose(value1 ,excludes ,node.left);
        value1 = choose(value1 ,excludes ,node.right);
        int value2 = value;
        if(!excludes.contains(node)) {
            if(node.right != null) {
                excludes.add(node.right);
            }
            if(node.left != null) {
                excludes.add(node.left);
            }

            //选择偷
            value2 = value2 + node.val;
            value2 = choose(value2 ,excludes ,node.left);
            value2 = choose(value2 ,excludes ,node.right);
            // 清理
            excludes.remove(node.left);
            excludes.remove(node.right);
        }

        return Math.max(value1,value2);
    }

    /**
     * 我们可以用 f(o) 表示选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和；
     * g(o) 表示不选择 o 节点的情况下，o 节点的子树上被选择的节点的最大权值和；l 和 r 代表 o 的左右孩子
     *
     */
    Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
    Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();

    public int rob2(TreeNode root) {
        dfs(root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        dfs(node.right);
        // 当 o被选中时，o的左右孩子都不能被选中，故 o被选中情况下子树上被选中点的最大权值和为 l和 r不被选中的最大权值和相加，即 f(o) = g(l) + g(r)
        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        // 当o不被选中时，o的左右孩子可以被选中或者不被选中。故最大值为: g(o) = Max(f(l)+g(l)) + Max(f(r)+g(r))
        g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }




    @Test
    public void test() {
        int[] array = {3,2,3,-1,3,-1,1};
        TreeNode root = convert(array);
        System.out.println(rob(root));
    }

    // 数组转节点
    public TreeNode convert(int[] array) {
        if(array.length == 0) {
            return null;
        }
        Map<Integer,TreeNode> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if(array[i] < 0) {
                continue;
            }
            TreeNode node = new TreeNode(array[i]);
            map.put(i,node);

            // 获取父节点
            int parentIndex = (i-1) /2;
            if(parentIndex >= 0 && parentIndex != i) {
                if((i-1) % 2 == 0) {
                    map.get(parentIndex).setLeft(node);
                } else {
                    map.get(parentIndex).setRight(node);
                }
            }
        }

        return map.get(0);
    }


}
