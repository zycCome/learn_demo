package com.zyc.learn_demo.algorithm.other_lc;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author zyc66
 * @date 2024/12/17 20:58
 **/
public class Leetcode2 {


    @Test
    public void change_case() {
        change(500,new int[]{2,7,13});
        System.out.println("----");
        change2(500,new int[]{2,7,13});
    }

    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length][amount+1];
        for(int j = 0;j <= amount;j++) {
            if(j >= coins[0] && j%coins[0] == 0) {
                dp[0][j] = 1;
            }
        }
        for(int i = 0;i < coins.length;i++) {
            dp[i][0] = 1;
        }

        for(int i =1;i < coins.length;i++ ) {
            for(int j = 1;j <= amount;j++) {
                if(j >= coins[i]) {
                    dp[i][j] = dp[i-1][j] + dp[i][j-coins[i]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        printArray2(dp);
        return dp[coins.length-1][amount];

    }

    public int change2(int amount, int[] coins) {
        int n = coins.length;
        int[][] f = new int[n + 1][amount + 1];
        f[0][0] = 1;   // 使用0种货币，凑0元钱,也是一种方案
        for (int i = 1; i <= n; i++) {
            int v = coins[i - 1];
            for (int j = 0; j <= amount; j++)
                for (int k = 0; k * v <= j; k++)
                    f[i][j] += f[i - 1][j - k * v];  //状态计算方程
        }
        printArray2(f);
        return f[n][amount];
    }


    public  void printArray2(int[][] arrays) {
        for(int i =0;i < arrays.length;i++) {
            System.out.println(Arrays.toString(arrays[i]));
        }
    }

    public double getPingFangGen(double d) {
        double x1 =d,x2;
        if(Math.abs(d-0) < 1e-7) {
            return 0;
        }
        while(true) {
            x2 = (x1 + d/x1)/2;
            if(Math.abs(x2-x1) < 1e-7) {
                return x2;
            }
            x1 = x2;
        }

    }

    @Test
    public void getPingFangGen_case() {
        System.out.println(getPingFangGen(0.25));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> queue = new ArrayDeque<Integer>();
        for(int i =0 ;i < k && i < nums.length;i++) {
            while(!queue.isEmpty()) {
                Integer pre = queue.pollLast();
                if(pre >= nums[i]) {
                    queue.addLast(pre);
                    break;
                }
            }
            queue.offer(nums[i]);
        }
        int size = Math.max(nums.length-k+1,1);
        int[] result = new int[size];
        for(int i =0;i < size ;i++) {
            Integer pre = queue.pollFirst();
            result[i] = pre;
            if(nums[i] != pre) {
                queue.addFirst(pre);
            }
            if(i + k <  nums.length) {
                while(!queue.isEmpty()) {
                    Integer pre2 = queue.pollLast();
                    if(pre2 >= nums[i + k]) {
                        queue.addLast(pre2);
                        break;
                    }
                }
                queue.offer(nums[i+k]);
            }

        }
        return result;
    }

    @Test
    public void maxSlidingWindow_case() {
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3)));
    }


    @Test
    public void binaryTreePaths_case() {
        TreeNode root = new TreeNode(1);
        System.out.println(binaryTreePaths(root));
    }


    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList();
        trackback(root,result,new StringBuilder());
        return result;

    }

    public void trackback(TreeNode node, List<String> result, StringBuilder stringBuilder) {
        if(node == null) {
            return;
        }

        int size = stringBuilder.length();
        if(size > 0 ) {
            stringBuilder.append("->");
        }
        stringBuilder.append(node.val);

        if(node.left == null && node.right == null) {
            result.add(stringBuilder.toString());
        } else {
            trackback(node.left,result,stringBuilder);
            trackback(node.right,result,stringBuilder);
        }
        stringBuilder.delete(size,stringBuilder.length());


    }


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
  }


  @Test
  public void combinationSum3() {
      List<List<Integer>> lists = combinationSum3(3, 7);
      System.out.println(lists);
  }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList();
        backtrack(1,k,n,result,new ArrayList(),0);
        return result;
    }

    public void backtrack(int start,int k ,int n, List<List<Integer>> result, List<Integer> temp,int sum) {
        if(sum == n && temp.size() == k)  {
            result.add(new ArrayList(temp));
            return;
        }
        if(start >9 || sum > n) {
            return;
        }


        for(int i = start; i <= 10;i++) {
            temp.add(i);
            backtrack(i+1,k,n,result,temp,sum+i);
            temp.remove(temp.size()-1);
        }

    }


    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList();
        backtrack(0,digits.toCharArray(),list,new StringBuilder());
        return list;
    }


    public void backtrack(int start,char[] chars,List<String> list,StringBuilder sb) {
        if(start >= chars.length) {
            if(sb.length() > 0 && sb.length() == chars.length) {
                list.add(sb.toString());
            }
            return;
        }
        for(int i = start;i < chars.length;i++ ) {
            char[] cs = getChars(chars[i]);
            for(int j =0;j < cs.length;j++) {
                int length = sb.length();
                sb.append(cs[j]);
                backtrack(i+1,chars,list,sb);
                sb.delete(length,sb.length());
            }
        }
    }

    public char[] getChars(char c) {
        int r = c - '2' ;
        if(r == 7) {
            return new char[]{'w','x','y','z'};
        }
        char[] cs = new char[3];
        cs[0] = (char)('a' + r *3);
        cs[1] = (char)('b' + r *3);
        cs[2] = (char)('c' + r *3);
        return cs;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList();
        backtrack(candidates,target,0,result,new ArrayList());
        return result;
    }

    public void backtrack(int[] candidates,int target,int start,List<List<Integer>> result,List<Integer> temp) {
        if(target == 0) {
            result.add(new ArrayList(temp));
            return;
        } else if(target < 0) {
            return;
        }
        if(start >= candidates.length) {
            return;
        }

        for(int i = start;i < candidates.length;i++) {
            temp.add(candidates[i]);
            backtrack(candidates,target - candidates[i],i,result,temp);
            temp.remove(temp.size() - 1);
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList();
        backtrack(s.toCharArray(),0,result,new ArrayList<>());
        return result;
    }

    public void backtrack(char[] chars,int startIndex,List<List<String>> result,List<String> temp) {
        if(startIndex >= chars.length) {
            result.add(new ArrayList(temp));
            return;
        }
        for(int i = startIndex;i < chars.length;i++) {
            if(isMatch(chars,startIndex,i)) {
                String subString = new String(chars,startIndex,i-startIndex+1);
                temp.add(subString);
                backtrack(chars,i+1,result,temp);
                temp.remove(temp.size()-1);
            }
        }
    }

    public boolean isMatch(char[] chars,int startIndex,int endIndex) {

        while(startIndex <= endIndex) {
            if(chars[startIndex--] != chars[endIndex--]) {
                return false;
            }
        }
        return true;
    }


    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList();
        backTrack(0,s.toCharArray(),result,new ArrayList());
        return result;
    }

    public void backTrack(int startIndex,char[] chars,List<String> result,List<String> temp) {
        if(startIndex >= chars.length) {
            if(temp.size() == 4) {
                String ipString = "";
                for(int i =0;i < temp.size();i++) {
                    if(i > 0) {
                        ipString += ".";
                    }
                    ipString += temp.get(i);
                }
                result.add(ipString);
            }
            return;
        }
        if(temp.size() >= 4) {
            return;
        }
        for(int i = startIndex;i < chars.length;i++) {
            String subString = new String(chars,startIndex,i-startIndex+1);
            if(isIp(subString,startIndex)) {
                temp.add(subString);
                backTrack(i+1,chars,result,temp);
                temp.remove(temp.size()-1);
            }
        }
    }

    public boolean isIp(String subString,int startIndex) {
        if(subString.length() > 1 && subString.charAt(startIndex) == '0') {
            return false;
        }

        int ip = Integer.parseInt(subString);
        if(ip > 255) {
            return false;
        }
        return true;
    }


    @Test
    public void wiggleMaxLength_case() {
        System.out.println(wiggleMaxLength(new int[]{1,7,4,9,2,5}));
    }

    public int wiggleMaxLength(int[] nums) {
        if(nums.length == 1) {
            return 1;
        }
        int count=0;
        for(int i = 0;i < nums.length;i++) {
            if(i == 0) {
                count++;
            } else if(i == nums.length-1) {
                if(nums[i] != nums[i-1]) {
                    count++;
                }
            } else {
                if(nums[i] > nums[i-1] && nums[i] > nums[i+1]) {
                    count++;
                } else if(nums[i] < nums[i-1] && nums[i] < nums[i+1]) {
                    count++;
                }
            }
        }
        return count;
    }


    @Test
    public void maxSubArray_case() {
        maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4});
    }

    public int maxSubArray(int[] nums) {
        int[] dp= new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for(int i =1;i < nums.length;i++) {
            if(dp[i-1] <0) {
                dp[i] = nums[i];
            } else {
                dp[i] = nums[i] + dp[i-1];
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }

    @Test
    public void permute_case() {
        System.out.println(permute(new int[]{1,2,3}));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList();
        backtrack(nums,0,result,new ArrayList(),new boolean[nums.length]);
        return result;
    }


    public void backtrack(int[] nums,int i,List<List<Integer>> result,List<Integer> temp,boolean[] used){
        if(i >= nums.length) {
            result.add(new ArrayList(temp));
            return;
        }
        for(int j =0;j < nums.length;j++) {
            if(!used[j]) {
                temp.add(nums[j]);
                used[j] = true;
                backtrack(nums,i+1,result,temp,used);
                temp.remove(temp.size()-1);
                used[j] = false;
            }

        }

    }

    @Test
    public void jump_case() {
        System.out.println(jump(new int[]{2,3,1,1,4}));
    }

    public int jump(int[] nums) {
        int i = 0;
        int count = 0;
        while(i < nums.length) {
            int curMaxStep =  i;
            int nextI = i;
            for(int j =1 ;j <= nums[i] && j+i < nums.length;j++) {
                if(nums[j+i]+j > curMaxStep) {
                    count++;
                    nextI = j+i;
                    curMaxStep = nums[j+i]+j;
                }
            }
            i = nextI;
        }
        return count;
    }

    @Test
    public void uniquePathsWithObstacles_case() {
        System.out.println(uniquePathsWithObstacles(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0;i < m;i++) {
            for(int j =0;j<n;j++) {
                if(obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 && j == 0) {
                        dp[i][j] = 1;
                    } else if(i == 0) {
                        dp[i][j] = dp[i][j-1];
                    } else if(j == 0 ) {
                        dp[i][j] = dp[i-1][j];
                    } else {
                        dp[i][j] = dp[i-1][j] + dp[i][j-1];
                    }
                }
            }
        }
        return dp[m-1][n-1];
    }

    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[2] = 1;
        for(int i =3;i <= n;i++ ) {
            int max = 0;
            for(int j=1;j < i;j++) {
                max = Math.max(j * Math.max(dp[i-j],i-j),max);
            }
            dp[i] = max;
        }
        return dp[n];
    }
}
