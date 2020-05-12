package org.itliu.algorithm.linkedList;

/**
 * @desc: 判断链表中是否存在回文字符串(Given a singly linked list, determine if it is a palindrome.)
 * <p>
 * 思路：
 * 使用快慢两个指针找到链表中点，慢指针每次前进一步，快指针每次前进两步。在慢指针前进的过程中，
 * 同时修改其 next 指针，使得链表前半部分反序。最后比较中点两侧的链表是否相等。
 * @author: itliu
 * @date: 2019/12/24 11:35
 * @version: 1.0
 */
public class PalindromeLinkedList {

    /**
     * 判断是否回文字符串
     *
     * @param head 单链表头结点
     * @return 是否回文字符串
     */
    public static boolean isPalindrome(ListNode head) {
        if (null == head || null == head.next) {
            return false;
        }

        /**
         * 因为是单向链表，需要保存前一个节点
         */
        ListNode prev = null;
        /**
         * 慢指针，一次走一步
         */
        ListNode slow = head;
        /**
         * 快指针，一次走两步
         */
        ListNode fast = head;

        /**
         * 只要快指针没有走到尾结点，则将慢指针经过的节点反转
         */
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode nextTmp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = nextTmp;
        }

        if (fast != null) {
            slow = slow.next;
        }

        while (slow != null) {
            if (slow.data != prev.data) {
                return false;
            }
            slow = slow.next;
            prev = prev.next;
        }

        return true;
    }
}
