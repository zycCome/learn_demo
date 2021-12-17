package com.zyc.learn_demo.algorithm.linklist;

import org.junit.Assert;
import org.junit.Test;

/**
 * 链表测试
 *
 * @author zhuyc
 * @date 2021/12/17 12:34
 */
public class LinkTest {

    @Test
    public void testLinkList() {
        LinkList<String> linkList = new LinkList<>();

        linkList.add("1");
        linkList.add("2");
        Assert.assertEquals(2, linkList.size());

        linkList.add(0, "0");
        Assert.assertEquals(3, linkList.size());
        linkList.add(3, "3");
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
            linkList.add(5, "4");
        });

        linkList.addFirst("-1");
        linkList.addLast("4");

        Assert.assertEquals(6, linkList.size());

        Assert.assertEquals(linkList.get(5), "4");
        linkList.remove(0);
        Assert.assertEquals(linkList.get(0), "0");
        Assert.assertEquals(linkList.get(4), "4");

        // 反向遍历
        for (int i = linkList.size() - 1; i >= 0; i--) {
            linkList.remove(i);
        }
        Assert.assertEquals(linkList.size(), 0);

        System.out.println("cuccess");


    }

}
