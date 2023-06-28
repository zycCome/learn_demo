package com.zyc.learn_demo.algorithm.hot100;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Description 环形链表
 * @Author zilu
 * @Date 2023/4/25 12:47 PM
 * @Version 1.0.0
 **/
public class Subject142 {


    /**
     * 快慢指针
     * 快指针赶上慢指针时，必然慢指针还没跑到一圈。(因为快指针相对慢指针按照1的速度逼近，因此也不会错过)
     * x= a+b
     * 2x = a+b+n（b+c）(剩余部分)
     * => (n-1)(b+c) + c = a
     * 因此快指针重置到头节点，然后以相同的速度跑，第一次见面就是环开始的地方
     *
     * @param head
     * @return
     */
    public ListNode hasCycle(ListNode head) {
        if(head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;


        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                // 重置
                fast = head;
                while(fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }

                return fast;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(0);
//        ListNode node4 = new ListNode(-4);

        node1.setNext(node2);
        node2.setNext(node1);
//        node3.setNext(node4);
//        node4.setNext(node2);

        Queue<ListNode> queue = new PriorityQueue((node11, node12) -> node1.val - node2.val );

        System.out.println(new Subject142().hasCycle(node1));
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}
