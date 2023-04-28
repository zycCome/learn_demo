package com.zyc.learn_demo.algorithm.hot100;

/**
 * @Description 环形链表
 * @Author zilu
 * @Date 2023/4/25 12:47 PM
 * @Version 1.0.0
 **/
public class Subject141 {


    /**
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if(head == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;


        while (true) {
            if(fast.next == null || fast.next.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                return true;
            }
        }



    }


    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }

    }
}
