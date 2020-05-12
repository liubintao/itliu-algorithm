package org.itliu.algorithm.linkedList;


/**
 * @desc: 将一个链表三等分
 * @author: itliu
 * @date: 2019/10/14 18:17
 * @version: 1.0
 */
public class LinkTrisect {

    public static ListNode[] trisect(ListNode listNode) {
        //快慢指针往后走,一个走一步，一个走两步，一个走三步
        ListNode left = listNode;
        ListNode middle = listNode.next;
        ListNode right = listNode.next.next;

        while (right.next != null) {

            //继续快慢指针往后走
            left = left.next;
            middle = middle.next.next;
            right = right.next.next.next;

            //如果走的快的发现为空 说明不存在环
            if (right == null) {
                return new ListNode[]{left, middle, null};
            }
        }
        return new ListNode[]{left, middle, right};


    }
}
