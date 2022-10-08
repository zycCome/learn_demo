package com.zyc.learn_demo.algorithm.hot100;

import org.junit.Test;

/**
 * @author zhuyc
 * @date 2022/10/04 07:48
 **/
public class Subject21 {

    /**
     * 合并两个有序链表
     * 双指针
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 新链表的尾
        ListNode tail = new ListNode();
        ListNode head = tail;
        while(list1 != null && list2 != null) {
            if(list1.val <= list2.val) {
                tail.next = list1;
                tail = list1;
                list1 = list1.next;

            } else {
                tail.next = list2;
                tail = list2;
                list2 = list2.next;

            }
        }
        if(list1 == null) {
            tail.next = list2;
        }
        if(list2 == null) {
            tail.next = list1;
        }
        return head.next;
    }

    @Test
    public void test() {
        ListNode convert = ListNode.convert(new int[]{1, 2, 4});
        ListNode convert2 = ListNode.convert(new int[]{1, 3, 4});
        ListNode listNode = mergeTwoLists(convert, convert2);
        while(listNode!= null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

}
