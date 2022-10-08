package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树的最近公共祖先
 * @author zhuyc
 * @date 2022/10/03 11:35
 **/
public class Subject235 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> list1 = new ArrayList();
        List<TreeNode> list2 = new ArrayList();
        findParents(root,p,list1);
        findParents(root,q,list2);

        int minSize = Math.min(list1.size(),list2.size());
        TreeNode result = root;
        for(int i = 0;i < minSize;i++) {
            if(list1.get(list1.size() -1 -i).val != list2.get(list2.size() - 1 -i).val) {
                break;
            }
            result = list1.get(list1.size() -1 -i);
        }
        return result;
    }


    public boolean findParents(TreeNode node,TreeNode air,List<TreeNode> parents) {
        if(node == null) {
            return false;
        }
        if(node == air) {
            parents.add(node);
            return true;
        }
        if(findParents(node.left,air,parents)) {
            parents.add(node);
            return true;
        } else if(findParents(node.right,air,parents)){
            parents.add(node);
            return true;
        }
        return false;


    }


    @Test
    public void test() {
        int[] array = new int[]{6,2,8,0,4,7,9,-1,-1,3,5};
        TreeNode root = TreeNode.convert(array);
        int p = 2;
        int q = 8;

        TreeNode result = lowestCommonAncestor(root, TreeNode.findOne(root, p), TreeNode.findOne(root, q));
        System.out.println(result);
    }

}
