package com.zyc.learn_demo.algorithm.hot100;

/**
 *
 * 19. 删除链表的倒数第 N 个结点
 * @author zhuyc
 * @date 2022/10/04 06:56
 **/
public class Subject19 {


    /**
     * 快慢指针
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slowPre = null;
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < n - 1; i++) {
            fast = fast.next;
        }

        while(fast.next != null) {
            fast = fast.next;
            slowPre = slow;
            slow = slow.next;
        }

        if(slowPre == null) {
            // 去除头节点
            slowPre = head.next;
            head.next = null;
            return slowPre;
        } else {

            slowPre.next = slow.next;
            slow.next = null;
            return head;
        }
    }

}
