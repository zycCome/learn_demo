package com.zyc.learn_demo.algorithm.linklist;

class MyLinkedList {
    private int _size;        // 链表长度
    private ListNode header;  // 头哨兵 index = _size
    private ListNode trailer; // 尾哨兵 index = -1

    public static class ListNode { // 双向结点
        int val;
        ListNode succ; // 后继
        ListNode pred; // 前缀

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode succ, ListNode pred) {
            this.val = val;
            this.succ = succ;
            this.pred = pred;
        }
    }

    public MyLinkedList() {
        this._size = 0;
        header = new ListNode();  // 头哨兵
        trailer = new ListNode(); // 尾哨兵
        header.succ = trailer;
        trailer.pred = header;
    }

    public int get(int index) {
        if (index < 0 || index >= _size) return -1;
        ListNode ptr = new ListNode();
        ptr = header.succ;
        while (index-- > 0) {
            ptr = ptr.succ;
        }

        return ptr.val;
    }

    public void addAtHead(int val) {
        ListNode temp = new ListNode(val);
        temp.succ = header.succ;
        header.succ.pred = temp;
        header.succ = temp;
        temp.pred = header;
        _size++;
    }

    public void addAtTail(int val) {
        ListNode temp = new ListNode(val);
        trailer.pred.succ = temp;
        temp.pred = trailer.pred;
        temp.succ = trailer;
        trailer.pred = temp;
        _size++;
    }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index > _size) return; // 可以在trailer前面插入
        ListNode temp = new ListNode(val);
        ListNode ptr = new ListNode();
        ptr = header.succ;
        while (index-- > 0) {
            ptr = ptr.succ;
        }

        // 在ptr的前面插入
        ptr.pred.succ = temp;
        temp.pred = ptr.pred;
        temp.succ = ptr;
        ptr.pred = temp;
        _size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= _size) return; // 删除范围[0, _size)
        ListNode ptr = new ListNode();
        ptr = header.succ;
        while (index-- > 0) {
            ptr = ptr.succ;
        }

        // 在ptr删除
        ptr.succ.pred = ptr.pred;
        ptr.pred.succ = ptr.succ;
        _size--;
    }
}

