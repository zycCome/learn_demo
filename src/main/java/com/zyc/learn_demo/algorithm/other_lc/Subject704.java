package com.zyc.learn_demo.algorithm.other_lc;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.*;

/**
 * @author zyc66
 * @date 2024/12/11 20:38
 **/
public class Subject704 {


    public static void main2(String[] args) {


        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] vec = new int[n];
        int[] p = new int[n];

        int presum = 0;
        for (int i = 0; i < n; i++) {
            vec[i] = scanner.nextInt();
            presum += vec[i];
            p[i] = presum;
        }

        while (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            int sum;
            if (a == 0) {
                sum = p[b];
            } else {
                sum = p[b] - p[a - 1];
            }
            System.out.println(sum);
        }
        scanner.close();

    }

    public int search(int[] nums, int target) {
        int[] result = new int[nums.length];
        int low = 0;
        int high = nums.length-1;
        int middle;
        while(low <= high && high >= 0) {
            middle = (low+high)/2;
            if(target == nums[middle]) {
                return middle;
            } else if (target > nums[middle]) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }

        }
        return -1;
    }


    @Test
    public void case1() {
        int[] params = new int[]{-1,0,3,5,9,12};
        System.out.println(search(params,9));
    }

    @Test
    public void case2() {
        int[] params = new int[]{5};
        System.out.println(search(params,5));
    }


    @Test
    public void case3() {
        System.out.println(isAnagram("rat","car"));
    }

    public boolean isAnagram(String s, String t) {
        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i)-'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            arr[t.charAt(i)-'a']--;
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void case4() {
        System.out.println(isHappy(19));
    }

    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while(n != 0 && !set.contains(n)) {
            set.add(n);
            n = getNumber(n);

        }

        return n == 1;
    }

    private int getNumber(int n) {
        int res = 0;
        while(n > 0) {
            int a = n %10;
            res += a * a;
            n = n/10;
        }
        return res;
    }


    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> aimMap = new HashMap<>();
        int[] result = new int[2];
        for(int i = 0;i < nums.length ;i++) {
            int aim = target - nums[i];
            if(aimMap.containsKey(aim)) {

                result[0] = i;
                result[1] = aimMap.get(aim);
                return result;
            }
            aimMap.put(nums[i],i);
        }
        return result;
    }


    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer,Integer> sumCountMap = new HashMap<>();
        for (int i : nums1) {
            for (int i1 : nums2) {
                int key = -i - i1;
                Integer count = sumCountMap.getOrDefault(key, 0);
                sumCountMap.put(key,count+1);
            }
        }
        int sum = 0;
        for (int i : nums3) {
            for (int i1 : nums4) {
                int aimKey = i + i1;
                Integer orDefault = sumCountMap.getOrDefault(aimKey,0);
                sum += orDefault;
            }
        }
        return sum;
    }



    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        // 找出a + b + c = 0
        // a = nums[i], b = nums[left], c = nums[right]
        for (int i = 0; i < nums.length; i++) {
            // 排序之后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果就可以了
            if (nums[i] > 0) {
                return result;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {  // 去重a
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重逻辑应该放在找到一个三元组之后，对b 和 c去重
                    while (right > left && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    while (right > left && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    right--;
                    left++;
                }
            }
        }
        return result;
    }




    @Test
    public void case5() {

    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for(int k = 0; k < nums.length;k++) {
            for(int i = k+1;i < nums.length;i++) {
                int left = i+1;
                int right = nums.length-1;
                while(left < right ) {
                    long sum = (long)nums[k] + nums[i] + nums[left] + nums[right];
                    if(sum == target) {
                        result.add(Arrays.asList(nums[k],nums[i],nums[right],nums[left]));
                        // 核心去重,两边都变化
                        while(left < right && nums[left] == nums[left+1]) {
                            left++;
                        }
                        while(left < right && nums[right] == nums[right-1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if(sum > target){
                        right--;
                    } else {
                        left++;
                    }
                }
                while(i < nums.length-1 && nums[i] == nums[i+1]) {
                    i++;
                }
            }
            while(k < nums.length-1 && nums[k] == nums[k+1] ) {
                k++;
            }
        }

        return result;
    }


    public String reverseStr(String s, int k) {
        int i = 0;
        char[] chars = s.toCharArray();
        while(i < s.length()) {
            if(s.length() >= i+ 2*k) {
                reverse(chars,i,i+k-1);
            } else if(s.length() >= i+k ) {
                reverse(chars,i,i+k-1);
            } else {
                reverse(chars,i,s.length()-1);
            }
            i = i+2*k;

        }
        return new String(chars);
    }

    // 定义翻转函数
    public void reverse(char[] ch, int i, int j) {
        for (; i < j; i++, j--) {
            char temp = ch[i];
            ch[i] = ch[j];
            ch[j] = temp;
        }
    }

    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        // 清除空格
        int lower = 0;
        int fast = 0;
        boolean preIsChar = false;
        while(fast < chars.length) {
            if(chars[fast] != ' ') {
                preIsChar = true;
                chars[lower++] = chars[fast];
            } else if(preIsChar) {
                preIsChar = false;
                // 前一个字符是字母，保留第一个空格
                chars[lower++] = chars[fast];
            } else {
                preIsChar = false;
            }
            fast++;
        }
        // 处理最后一个空格
        int length = lower;
        if(!preIsChar){
            length -= 1;
        }
        // 反转字符串
        reverse(chars,0,length-1);
        // 反转单词

        int wordStart = 0;
        for (int i = 0; i < length ;i++) {
            if(chars[i] == ' ') {
                reverse(chars,wordStart,i-1);
                wordStart = i+1;
            } else if(i == length-1){
                reverse(chars,wordStart,i);
            }
        }
        return new String(chars,0,length);
    }


    @Test
    public void case6() {
        System.out.println( reverseWords("    the sky    is blue  "));

    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(stack.empty()) {
                stack.push(c);
            } else {
                Character pop = stack.peek();
                if(pop == '(' && c == ')') {
                    stack.pop();
                } else if(pop == '[' && c == ']') {
                    stack.pop();
                } else if(pop == '{' && c == '}') {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }
        return stack.empty();
    }


    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty() || stack.peek() != c) {
                stack.push(c);
            } else {
                stack.pop();
            }
        }
        int size = stack.size();
        char[] result = new char[size--];
        for(;size >= 0;size--) {
            result[size] = stack.pop();
        }
        return new String(result);
    }


    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if(token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if(token.equals("/")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b / b);
            } else if(token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if(token.equals("-")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b - a);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }


    @Test
    public void case7() {
        String[] a = {"4","13","5","/","+"};
        System.out.println( evalRPN(a));

    }


    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> countMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer count = countMap.getOrDefault(nums[i], 0);
            countMap.put(nums[i],count+1);
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((pair1,pair2) -> {
            return pair1[1] - pair2[1];
        });
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if(priorityQueue.size() >= k) {
                if(priorityQueue.peek()[1] < entry.getValue()) {
                    priorityQueue.poll();
                    priorityQueue.add(new int[] {entry.getKey(),entry.getValue()});
                }
            } else {
                priorityQueue.add(new int[] {entry.getKey(),entry.getValue()});
            }
        }
        int size = priorityQueue.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = priorityQueue.poll()[0];
        }
        return result;
    }

    @Test
    public void case8() {
        System.out.println( topKFrequent(new int[]{1,1,1,2,2,3},2));

    }


    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backTracking(1,n,k,result,new ArrayList<>());
        return result;
    }

    public void backTracking(int start,int end,int k,List<List<Integer>> result,List<Integer> temp) {
        if(temp.size() == k) {
            result.add(new ArrayList<>(temp));
            return;
        }
        int leftSize = k - temp.size();
        while(start <= (end - leftSize)+1) {
            temp.add(start);
            backTracking(start+1,end,k,result,temp);
            temp.remove((Integer) start);
            start++;
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backTracking2(1,9,k,result,new ArrayList<>(),n);
        return result;
    }


    public void backTracking2(int start,int end,int k,List<List<Integer>> result,List<Integer> temp,int n) {
        if(temp.size() == k) {
            int sum = 0;
            for(int i = 0 ;i < k ;i++) {
                sum += temp.get(i);
            }
            if(sum == n) {
                result.add(new ArrayList<>(temp));
            }
            return;
        }
        int leftSize = k - temp.size();
        while(start <= (end - leftSize)+1) {
            temp.add(start);
            backTracking2(start+1,end,k,result,temp,n);
            temp.remove((Integer) start);
            start++;
        }
    }

    @Test
    public void case9() {
        System.out.println( combine(4,2));

    }


    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backTracking(s.toCharArray(),0,result,new LinkedList<>());
        return result;
    }

    public void backTracking(char[] chars ,int startIndex,List<List<String>> result,LinkedList<String> temp) {
        if(startIndex >= chars.length) {
            // 所有切到最后一个位置了
            result.add(new ArrayList<>(temp));
            return;
        }
        // startIndex 表示还没切的位置，包含
        for (int i = startIndex; i < chars.length; i++) {
            if(!isPalindrome(chars,startIndex,i)) {
                // 从下一个位置开始切
                continue;
            }
            String s = new String(chars, startIndex, i - startIndex + 1);
            temp.addLast(s);
            backTracking(chars,i+1,result,temp);
            temp.removeLast();
        }
    }

    private boolean isPalindrome(char[] chars, int startIndex, int i) {
        while(startIndex <= i) {
            if(chars[i] != chars[startIndex]) {
                return false;
            }
            startIndex++;
            i--;
        }
        return true;

    }


    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backTracking3(nums,0,result,new LinkedList<>());
        result.add(new ArrayList<>());
        return result;
    }

    public void backTracking3(int[] nums,int startIndex,List<List<Integer>> result,LinkedList<Integer> temp) {
        if(startIndex >= nums.length) {
            // 说明没有元素可以枚举了
            return;
        }
        for(;startIndex < nums.length;startIndex++) {
            temp.add(nums[startIndex]);
            result.add(new ArrayList<>(temp));
            backTracking3(nums,startIndex+1,result,temp);
            temp.removeLast();
        }
    }


    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int satisfy = 0;
        int cookieIndex = 0;
        for (int i = 0; i < g.length; i++) {
            int need = g[i];
            while(cookieIndex < s.length) {
                if(s[cookieIndex] >= need) {
                    satisfy++;
                    cookieIndex++;
                    break;
                } else {
                    cookieIndex++;
                }
            }
        }
        return satisfy;
    }

    public int maxProfit(int[] prices) {
        int result = 0;
        for(int i =1;i< prices.length;i++) {
            if((prices[i] -prices[i - 1] ) >0) {
                result += prices[i] -prices[i - 1];
            }
        }
        return result;
    }

    public boolean canJump(int[] nums) {
        int max = nums[0];
        for(int i = 1;i < nums.length && i <= max;i++) {
            int cmax = i + nums[i] ;
            if(cmax > max) {
                max = cmax;
            }
        }
        return max >= nums.length - 1;
    }

    @Test
    public void canJump_case() {
        System.out.println( canJump(new int[]{1,2,3}));

    }


    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        trackBacking90(0,nums, result,new LinkedList<>());
        result.add(new ArrayList<>());
        return result;
    }

    public  void trackBacking90(int index,int[] nums,List<List<Integer>> result,LinkedList<Integer> temp) {
        if(index >= nums.length) {
            return;
        }

        int preValue = 0;
        boolean setPreValue = false;
        // 有重复元素，需要当前层去重
        for(int i = index;i < nums.length;i++) {
            if(setPreValue) {
                if(nums[i] == preValue) {
                    continue;
                }
            } else {
                setPreValue = true;
            }
            preValue = nums[i];
            temp.add(nums[i]);
            result.add(new ArrayList<>(temp));
            trackBacking90(i+1,nums,result,temp);
            temp.removeLast();
        }
    }

    @Test
    public void subsetsWithDup_case() {
        System.out.println(subsetsWithDup(new int[]{1,2,2}));
    }


    List<List<Integer>> resultList;
    LinkedList<Integer> tempList;


    public List<List<Integer>> findSubsequences(int[] nums) {
        resultList= new ArrayList<>();
        tempList = new LinkedList<>();
        trackBacking491(0,nums);
        return resultList;
    }

    public  void trackBacking491(int index,int[] nums) {
        if(index >= nums.length) {
            return;
        }

        Set<Integer> set = new HashSet<>();
        for(int i = index;i < nums.length;i++) {
            if(tempList.isEmpty() || tempList.getLast() <= nums[i]) {
                if(!set.add(nums[i])) {
                    continue;
                }
                tempList.addLast(nums[i]);
                if(tempList.size() >= 2) {
                    resultList.add(new ArrayList<>(tempList));
                }
                trackBacking491(i+1,nums);
                tempList.removeLast();
            }
        }
    }

    @Test
    public void findSubsequences_case() {
        int[] arr = new int[] {1,2,3,4,5,6,7,8,9,10,1,1,1,1,1};
        Set<String> set = new HashSet<>();
        List<List<Integer>> subsequences = findSubsequences(arr);
        System.out.println(subsequences);

        for (List<Integer> subsequence : subsequences) {
            if(!set.add(StrUtil.join(",",subsequence))) {
                System.out.println(subsequence);
            }
            for (int i = 1; i < subsequence.size(); i++) {
                if(subsequence.get(i) < subsequence.get(i-1)) {
                    System.out.println(subsequence);
                }
            }
        }
    }


    public List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < nums.length;i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {  // 去重a
                continue;
            }
            int need = -nums[i];
            int slow = i+1;
            int fast = nums.length -1 ;
            while(slow < fast) {
                int total = nums[slow] + nums[fast];
                if(total == need) {
                    result.add(Arrays.asList(nums[i],nums[slow],nums[fast]));
                    while(slow < fast && nums[fast-1] == nums[fast]) {
                        fast--;
                    }
                    fast--;
                    while(slow < fast && nums[slow+1] == nums[slow]) {
                        slow++;
                    }
                    slow++;
                } else if(total > need) {
                    fast--;
                } else {
                    slow++;
                }
            }
        }

        return result;
    }


    @Test
    public void threeSum2_case() {
        threeSum2(new int[] {1,-1,1,0});
    }


    @Test
    public void negative_mode_case() {
        System.out.println(-1 % 10);
    }


    public static void main(String[] args) {
        Scanner scaner = new Scanner(System.in);
        int m = scaner.nextInt();
        int n = scaner.nextInt();
        int[] space = new int[m];
        int[] value = new int[m];
        for (int i = 0; i < m ; i++ ) {
            space[i] = scaner.nextInt();
        }
        for (int i = 0; i < m ; i++ ) {
            value[i] = scaner.nextInt();
        }
        int[][] dp = new int[m][n+1];
        for(int i =0;i < n+1;i++) {
            if(i >= space[0]) {
                dp[0][i] = value[0];
            }
        }
        for(int i =1;i < m;i++) {
            for(int j =1;j < n+1;j++) {
                int a = dp[i-1][j];
                int b = j >= space[i] ? dp[i-1][j - space[i]] + value[i] : 0;
                dp[i][j] = Math.max(a,b);

            }
        }
        System.out.println(dp[m-1][n]);
    }


    public boolean canPartition(int[] nums) {
        int total = 0;
        for(int i = 0;i < nums.length;i++) {
            total += nums[i];
        }
        if(total % 2 == 1) {
            return false;
        }
        int target = total /2;

        int[] dp = new int[target + 1];
        for(int i =0 ;i < nums.length;i++) {
            for(int j = target; j >= nums[i];j--) {
                dp[j] = Math.max(dp[j],dp[j-nums[i]] + nums[i]);
            }
        }

        return false;
    }

    @Test
    public void canPartition_case() {
        System.out.println(canPartition(new int[]{14,9,8,4,3,2}));
    }


    public int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for(int i = 0;i< nums.length;i++) {
            total += nums[i];
        }
        if (Math.abs(target) > total) {
            return 0;
        }
        if((total + target)%2 != 0) {
            return 0;
        }
        int left = (total + target)/2;

        int[][] dp = new int[nums.length][left+1];
        dp[0][0] = 1;
        for(int i=1;i <= left;i++) {
            dp[0][i] = (nums[0] == i ? 1:0);
        }

        int zeroNum = 0;
        for(int i=0;i < nums.length;i++) {
            if(nums[i] == 0) {
                zeroNum++;
            }
            dp[i][0] = (int)Math.pow(2.0,zeroNum);
        }


        for(int i = 1;i < nums.length;i++) {
            for(int j= 1; j <= left;j++ ) {
                if(j < nums[i] ) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]];
                }
            }
        }

        return dp[nums.length-1][left];
    }


    @Test
    public void findTargetSumWays_case() {
        System.out.println(findTargetSumWays2(new int[]{0},0));
    }

    public int findTargetSumWays2(int[] nums, int target) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) sum += nums[i];

        //如果target的绝对值大于sum，那么是没有方案的
        if (Math.abs(target) > sum) return 0;
        //如果(target+sum)除以2的余数不为0，也是没有方案的
        if ((target + sum) % 2 == 1) return 0;

        int bagSize = (target + sum) / 2;
        int[] dp = new int[bagSize + 1];
        dp[0] = 1;

        for (int i = 0; i < nums.length; i++) {
            for (int j = bagSize; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }

        return dp[bagSize];
    }

    public int lengthOfLongestSubstring(String s) {
        Deque<Integer> dataStack;

        Set<Character> set = new HashSet();
        char[] chars = s.toCharArray();
        int max = 0;
        int cur = 0;
        for(int i =0;i < chars.length;i++) {
            Character cr = chars[i];
            while(set.contains(cr)) {
                int startIndex = i-cur;
                set.remove(chars[startIndex]);
                cur--;
            }
            if(set.add(cr)) {
                cur++;
            }

            if(cur > max) {
                max = cur;
            }

        }
        return max;
    }



    class MinStack {

        private Deque<Integer> dataStack;
        private Deque<Integer> minStack;

        public MinStack() {
            dataStack = new ArrayDeque();
            minStack = new ArrayDeque();
        }

        public void push(int val) {
            if(dataStack.isEmpty() || minStack.getLast() >= val) {
                dataStack.addLast(val);
                minStack.addLast(val);
            } else {
                dataStack.addLast(val);
            }

        }

        public void pop() {
            if(dataStack.isEmpty()) {
                return;
            }
            if(dataStack.pollLast().equals(minStack.getLast())) {
                minStack.pollLast();
            }
        }

        public int top() {
            return dataStack.getLast();
        }

        public int getMin() {
            return minStack.getLast();
        }
    }

    @Test
    public void restoreIpAddresses_case() {
        System.out.println(restoreIpAddresses("101023"));
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList();

        backtrack(0,0,s.toCharArray(),result,new ArrayList());
        return result;
    }

    public void backtrack(int start,int count,char[] chars,List<String> result,List<Integer> temp) {
        if(count > 3) {
            return;
        }
        int mayMax = start + (4 - count) * 3 ;
        if(chars.length > mayMax ) {
            return;
        }

        for(int i = start;i < start+3 && i < chars.length;i++) {
            int number = getValidNumber(chars,start,i);
            if(number >= 0) {
                if(count == 3) {
                    if(i == chars.length - 1) {
                        String str ="";
                        for(int k =0;k<3;k++) {
                            str += temp.get(k);
                            str += ".";
                        }
                        str += number;
                        result.add(str);
                    }
                    continue;
                }
                temp.add(number);
                backtrack(i+1,count+1,chars,result,temp);
                temp.remove(temp.size()-1);
            }
        }
    }


    public int getValidNumber(char[] chars,int i,int j) {
        if(j >i && chars[i] == '0') {
            return -1;
        }
        int number = 0;
        int c= 1;
        for(;j >= i;j--) {
            number += (chars[j] - '0') * c;
            c = c *10;
        }
        if(number > 255) {
            return -1;
        }
        return number;
    }


    @Test
    public void sortArray_case() {
        printArray(sortArray(new int[] {110,100,0}));
    }

    public int[] sortArray(int[] nums) {
        quickSort(nums,0,nums.length-1);
        return nums;
    }

    private void printArray(int[] nums) {
        for (int num : nums) {
            System.out.print(num);
            System.out.print("\t");
        }
        System.out.println();
    }



    public void quickSort(int[] nums,int start,int end) {
        if(start >= end) {
            return;
        }
        int middle = partition(nums,start,end);
        quickSort(nums,start,middle-1);
        quickSort(nums,middle+1,end);
    }

    public int partition(int[] nums,int start,int end) {
        Random random = new Random();
        int randomIndex = random.nextInt(end-start+1)+start;

        int pivot = nums[randomIndex];
        nums[randomIndex] = nums[end];
        nums[end] = pivot;
        while(start < end) {
            while( start < end && nums[start] <= pivot) {
                start ++;
            }
            nums[end] = nums[start];
            while(start < end && nums[end] >= pivot) {
                end --;
            }
            nums[start] = nums[end];
        }
        nums[start] = pivot;
        return start;
    }




}
