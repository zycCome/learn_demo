package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/10/03 10:58
 **/
public class Subject234 {

    // 快慢指针找到中位数，然后开始翻转链表，并比较
    // 反转后的链表可能长度不一样，以短的为准
    public boolean isPalindrome(ListNode head) {
        if (head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.val == head.next.val;
        }

        // 快慢指针寻找中位数
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 以slow的下一位开始翻转链表，如果是奇数，则前半部分的链表多一位，但这一位也不用比较
        ListNode newHead = reverseLink(slow.next);
        do {
            if (newHead.val != head.val) {
                return false;
            }
            newHead = newHead.next;
            head = head.next;
        } while (newHead != null);
        return true;
    }


    public ListNode reverseLink(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode newHead = reverseLink(head.next);
        head.next.next = head;
        // 这一步必须，否则会循环链表
        head.next = null;

        return newHead;

    }

    @Test
    public void test() {
        ListNode convert = convert(new int[]{1, 2, 3, 1});
        System.out.println(isPalindrome(convert));
    }

    @Test
    public void test2() {
        ListNode convert = convert(new int[]{1, 2,  1});
        System.out.println(isPalindrome(convert));
    }

    @Test
    public void test3() {
        ListNode convert = convert(new int[]{1,   2});
        System.out.println(isPalindrome(convert));
    }

    public ListNode convert(int[] array) {
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
