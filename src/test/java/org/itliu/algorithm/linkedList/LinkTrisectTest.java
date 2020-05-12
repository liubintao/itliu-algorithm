package org.itliu.algorithm.linkedList;

import org.junit.Test;

/**
 * @desc:
 * @author: itliu
 * @date: 2019/10/14 19:05
 * @version: 1.0
 */
public class LinkTrisectTest {

    @Test
    public void trisectTest() {

        ListNode<String> listNode1 = new ListNode<>("1");
        ListNode<String> listNode2 = new ListNode<>("2");
        ListNode<String> listNode3 = new ListNode<>("3");

        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ListNode[] triple = LinkTrisect.trisect(listNode1);
        System.out.println(triple[0]);
        System.out.println(triple[1]);
        System.out.println(triple[2]);
    }
}