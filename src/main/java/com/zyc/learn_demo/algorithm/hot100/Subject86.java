package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/12 12:51 PM
 * @Version 1.0.0
 **/
public class Subject86 {


    public ListNode partition(ListNode head, int x) {
        ListNode large = new ListNode();
        ListNode small = new ListNode();
        ListNode lastSmall = small;
        ListNode lastLarge = large;

        while(head != null) {
            if(head.val < x) {
                lastSmall.next = head;
                lastSmall = head;
            } else {
                lastLarge.next = head;
                lastLarge = head;
            }

            head = head.next;
        }

        if(small.next == null) {
            lastLarge.next = null;
            return large.next;
        }
        lastSmall.next = null;
        lastLarge.next = null;


        lastSmall.next = large.next;
        return small.next;
    }


    @Test
    public void test() {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(4);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(2);
        ListNode e = new ListNode(5);
        ListNode f = new ListNode(2);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        ListNode partition = partition(a, 3);
        System.out.println(partition);
    }

}
