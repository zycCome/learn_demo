package com.zyc.learn_demo.algorithm.hot100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description
 * @Author zilu
 * @Date 2022/10/8 12:50 PM
 * @Version 1.0.0
 **/
public class Subject102 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList();
        if(root == null) {
            return result;
        }
        Map<TreeNode,Integer> nodeLevel = new HashMap<>();
        int currentLevel = 0;
        List<Integer> levelList = null;
        ConcurrentLinkedQueue<TreeNode> queue = new ConcurrentLinkedQueue();
        queue.add(root);

        nodeLevel.put(root,1);
        while(queue.peek() != null) {
            TreeNode node = queue.poll();
            Integer level = nodeLevel.get(node);
            if(currentLevel != level) {
                // 开始新的一层
                levelList = new ArrayList<>();
                result.add(levelList);
                currentLevel= level;
            }
            levelList.add(node.val);
            if(node.left != null) {
                queue.add(node.left);
                nodeLevel.put(node.left,level+1);
            }
            if(node.right != null) {
                queue.add(node.right);
                nodeLevel.put(node.right,level+1);
            }

        }

        return result;
    }


    public static class TreeNode {
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
  }
}
