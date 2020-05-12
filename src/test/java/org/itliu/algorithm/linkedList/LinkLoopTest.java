package org.itliu.algorithm.linkedList;


import org.junit.Assert;
import org.junit.Test;

/**
 * @desc:
 * @author: itliu
 * @date: 2019/10/14 18:08
 * @version: 1.0
 */
public class LinkLoopTest {

    @Test
    public void noLoopTest() {
        ListNode<String> listNode1 = new ListNode<>("1");
        ListNode<String> listNode2 = new ListNode<>("2");
        ListNode<String> listNode3 = new ListNode<>("3");

        listNode1.next = listNode2;
        listNode2.next = listNode3;

        Assert.assertFalse(LinkLoop.isLoop(listNode1));
    }

    @Test
    public void isLoopTest() {
        ListNode<String> listNode1 = new ListNode<>("1");
        ListNode<String> listNode2 = new ListNode<>("2");
        ListNode<String> listNode3 = new ListNode<>("3");

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode1;

        Assert.assertTrue(LinkLoop.isLoop(listNode1));
    }
}