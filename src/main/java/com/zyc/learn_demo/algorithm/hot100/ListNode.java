package com.zyc.learn_demo.algorithm.hot100;

/**
 * @author zhuyc
 * @date 2022/10/04 06:57
 **/
public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }


    public static ListNode convert(int[] array) {
        ListNode pre = null;
        ListNode result = null;
        for (int i = 0; i < array.length; i++) {
            ListNode node = new ListNode(array[i]);
            if(i == 0) {
                result = node;
            } else {
                pre.next = node;
            }
            pre = node;
        }
        return result;
    }

}
