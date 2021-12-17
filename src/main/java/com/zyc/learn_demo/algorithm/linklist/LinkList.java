package com.zyc.learn_demo.algorithm.linklist;

/**
 * 单向链表demo
 *
 * @author zhuyc
 * @date 2021/12/16 12:49
 */
public class LinkList<E> {

    private LinkNode<E> header = new LinkNode(null, null);
    private LinkNode<E> tail = header;

    private int size = 0;

    public void addLast(E value) {
        LinkNode<E> newNode = new LinkNode(value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    public void addFirst(E value) {
        LinkNode<E> newNode = new LinkNode(value, null);
        newNode.next = header.next;
        header.next = newNode;
        // 如果之前队列为空，则tail指向最新插入的节点。否则不影响tail
        if (size == 0) {
            tail = newNode;
        }
        size++;
    }

    /**
     * 在指定位置插入节点
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        // 插入位置检测
        checkPositionIndex(index);

        LinkNode preNode = header;
        for (int i = 1; i <= index; i++) {
            preNode = preNode.next;
        }
        LinkNode linkNode = new LinkNode(element, preNode.next);
        linkNode.next = preNode.next;
        preNode.next = linkNode;
        // 如果插入最后一个节点，则tail指向最新节点
        if (size == index) {
            tail = linkNode;
        }
        size++;
    }

    /**
     * 插入最后一个
     *
     * @param element
     */
    public void add(E element) {
        LinkNode preNode = tail;

        tail.next = new LinkNode(element, null);
        tail = tail.next;
        size++;
    }


    /**
     * 删除指定位置节点
     *
     * @param index
     */
    public void remove(int index) {
        // 位置检测
        checkElementIndex(index);

        LinkNode preNode = header;
        for (int i = 1; i <= index; i++) {
            preNode = preNode.next;
        }
        preNode.next = preNode.next.next;
        // 如果移除的是最后一个节点，则tail指向前一个节点
        if (index == size - 1) {
            tail = preNode;
        }
        size--;
    }

    /**
     * 获取指定位置元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        // 位置检测
        checkElementIndex(index);

        LinkNode<E> result = header;
        for (int i = 0; i <= index; i++) {
            result = result.next;
        }
        return result.value;
    }

    /**
     * 位置检测
     * 0 <= posistion <= size
     * @param index
     */
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 0 <= 元素位置 < size
     * @param index
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 返回链表中节点数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 构造越界信息
     *
     * @param index
     * @return
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    static class LinkNode<E> {

        private E value;

        private LinkNode next;

        public LinkNode(E value, LinkNode next) {
            this.value = value;
            this.next = next;
        }
    }

}
