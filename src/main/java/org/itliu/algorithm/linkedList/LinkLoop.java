package org.itliu.algorithm.linkedList;


/**
 * @desc: Function:是否是环链表，采用快慢指针，一个走的快些一个走的慢些 如果最终相遇了就说明是环就相当于在一个环形跑道里跑步，
 * 速度不一样的最终一定会相遇。
 * @author: itliu
 * @date: 2019/10/14 17:49
 * @version: 1.0
 */
public class LinkLoop {

    public static boolean isLoop(ListNode listNode) {

        //快慢指针往后走,一个走一步，一个走两步
        ListNode slow = listNode;
        ListNode fast = listNode.next;

        while (slow.next != null) {
            Object slowData = slow.data;
            Object fastData = fast.data;

            //说明是有环的
            if (slowData.equals(fastData)) {
                return true;
            }

            //如果只有两个节点，但确不是环形链表的情况，判断NPE
            if (fast.next == null) {
                return false;
            }

            //继续快慢指针往后走
            slow = slow.next;
            fast = fast.next.next;

            //如果走的快的发现为空 说明不存在环
            if (fast == null) {
                return false;
            }
        }
        return false;
    }
}
