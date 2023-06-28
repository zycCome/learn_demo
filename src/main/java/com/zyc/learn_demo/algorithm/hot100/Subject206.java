package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @Description
 * @Author zilu
 * @Date 2023/5/18 12:54 PM
 * @Version 1.0.0
 **/
public class Subject206 {

    public ListNode reverseList(ListNode head) {
        if(head== null || head.next == null) {
            return head;
        }
        ListNode a = head;
        ListNode b = head.next;
        ListNode c = head.next.next;
        do {
            b.next = a;
            a = b;
            b = c;
            if(c != null) {
                c = c.next;
            }
        } while(b != null);
        // 别忘了1还指向2
        head.next = null;
        return a;

    }


    /**
     * 递归解法
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        if(head.next == null) {
            return head;
        }
        // 不用暂存，因为head的next没变
//        ListNode headNext = head.next;
        ListNode reverse = reverse(head.next);
        head.next.next = head;
        // 不能循环
        head.next = null;
        return reverse;

    }

    /**
     * 对于递归算法，最重要的就是明确递归函数的定义。具体来说，我们的 reverse 函数定义是这样的：
     *
     * 输入一个节点 head，将「以 head 为起点」!!!的链表反转，并返回反转之后的头结点。
     * @param head
     * @return
     */
    public ListNode reverse(ListNode head) {
        if(head.next == null) {
            return head;
        }
        ListNode headNext = head.next;
        ListNode reverse = reverse(head.next);
        headNext.next = head;
        return reverse;
    }



    @Test
    public void test1() {
        ListNode head = ListNode.convert(new int[]{1, 2, 3,4,5});
        ListNode reverseList = reverseList2(head);
        System.out.println("end");
    }
}
