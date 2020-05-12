package org.itliu.algorithm.linkedList;

/**
 * @desc: 把给定值都删除
 * @author: itliu
 * @date: 2020/4/21 0:02
 * @version: 1.0
 */
public class DeleteGivenValue {

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    //因为要删的节点可能会是头节点，所以要返回新的头节点
    public static Node removeValue(Node head, int num) {
        //step1:先删掉所有与num相同的头节点，比如链表中的前几个节点的值都为num
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }

        // head来到 第一个不需要删的位置
        // 关键点：指针的移动，pre 要跟着cur往前提，当需要删的时候能找到正确的pre
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
