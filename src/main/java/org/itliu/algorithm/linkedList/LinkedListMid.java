package org.itliu.algorithm.linkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 快慢指针求链表中点问题
 * 解决思路：1、设置好base case
 * 2、设置好起点
 * 3、直接返回slow
 * @author: itliu
 * @date: 2020/4/28 9:46
 * @version: 1.0
 */
public class LinkedListMid {

    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public static Node midOrUpMidNode(Node head) {
        /**
         * base case
         * 1.没有节点       --------->  返回null没错
         * 2.只有头结点      --------->  返回head没错
         * 3.只有两个节点    --------->  返回head也就是上中点没错
         */
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        /**
         * 设置起点
         * 走到这一步证明链表最少有三个节点了
         * 当只有三个节点的情况：慢指针走一步，快指针走两步，返回慢指针，则正好处于中点
         * 当只有四个节点的情况：慢指针走一步，快指针走两步，返回慢指针，则正好处于上中点
         */
        Node slow = head.next;
        Node fast = head.next.next;
        //快慢指针可以同时往下走
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     */
    public static Node midOrDownMidNode(Node head) {
        /**
         * base case
         * 1.没有节点       --------->  返回null没错
         * 2.只有头结点      --------->  返回head没错
         * 3.只有两个节点    --------->  就不能返回head了，需要在下面的逻辑里执行
         */
        if (head == null || head.next == null) {
            return head;
        }

        /**
         * 设置起点
         * 走到这一步证明链表最少有2个节点了
         * 当只有2个节点的情况：慢指针走一步，快指针走一步，返回慢指针，则正好处于下中点
         * 0  1
         *    s,f
         * 当只有3个节点的情况：慢指针走一步，快指针走一步，返回慢指针，则正好处于中点
         * 0  1     2
         *    s,f
         * 当只有4个节点的情况：慢指针再走一步，快指针再走两步，返回慢指针，则正好处于下中点
         * 0  1  2  3
         *       s  f
         */
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * base case
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    public static Node midOrUpMidPreNode(Node head) {
        /**
         * 1.没有节点       --------->  返回null没错
         * 2.只有头结点      --------->  返回null
         * 3.只有两个节点    --------->  返回null
         * 4.只有三个节点    --------->  中点就是中点, 返回head节点,需要后面的逻辑执行
         */
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        /**
         * 设置起点
         * 走到这一步证明链表最少有3个节点了
         * 当只有3个节点的情况：慢指针指向head，快指针走两步，返回慢指针，则正好处于中点前一个
         * 0   1   2
         * s       f
         * 当只有4个节点的情况：慢指针指向head，快指针不动，返回慢指针，则正好处于上中点前一个
         * 0   1   2   3
         * s       f
         * 当只有5个节点的情况：慢指针再走一步，快指针再走两步，返回慢指针，则正好处于中点前一个
         * 0   1   2   3   4
         *     s           f
         */
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * base case
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    public static Node midOrDownMidPreNode(Node head) {
        /**
         * 1.没有节点       --------->  返回null没错
         * 2.只有头结点      --------->  返回null
         * 3.只有两个节点    --------->  返回head，跟前两部处理不一致，需要后面的逻辑执行
         */
        if (head == null || head.next == null) {
            return null;
        }

        /**
         * 设置起点
         * 走到这一步证明链表最少有2个节点了
         * 当只有2个节点的情况：慢指针指向head，快指针走一步，返回慢指针，则正好处于下中点前一个
         * 0   1
         * s   f
         * 当只有3个节点的情况：慢指针指向head，快指针不动，返回慢指针，则正好处于中点前一个
         * 0   1   2
         * s   f
         * 当只有4个节点的情况：慢指针再走一步，快指针再走两步，返回慢指针，则正好处于下中点前一个
         * 0   1   2   3
         *     s       f
         */
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }

        List<Node> list = new ArrayList();
        Node cur = head;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        return list.get((list.size() - 1) / 2);
    }

    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }

        List<Node> list = new ArrayList();
        Node cur = head;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        return list.get(list.size() / 2);
    }

    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        List<Node> list = new ArrayList();
        Node cur = head;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        return list.get((list.size() - 3) / 2);
    }

    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }


    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        ans1 = midOrUpMidNode(test);
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrUpMidPreNode(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = midOrDownMidPreNode(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

    }
}
