package org.itliu.algorithm.linkedList;

import java.util.ArrayList;

/**
 * @desc: 单链表和双链表如何反转
 * @author: itliu
 * @date: 2020/4/20 23:10
 * @version: 1.0
 */
public class Reverse {

    private static class Node<T> {
        public T value;
        public Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    public static class DoubleNode<T> {
        public T value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(T value) {
            this.value = value;
        }
    }

    public static Node reverseLinkedList(Node head) {
        //开始的时候就认为这两个变量没用
        Node pre = null;
        Node next = null;

        while (head != null) {
            //先将当前节点的next节点保存，后面要用，防止丢失
            next = head.next;
            //将当前节点的next指针指向pre实现反转
            head.next = pre;
            //把当前节点的引用赋给pre，下次遍历使用
            pre = head;
            //当前节点的next节点下次遍历时就变成了head节点
            head = next;
        }
        //当head==null时，就说明已经遍历完了，遍历的最后一步pre已经变成了新的头节点
        return pre;
    }

    public static DoubleNode reverseDoubleList(DoubleNode head) {
        //开始的时候就认为这两个变量没用
        DoubleNode pre = null;
        DoubleNode next = null;

        while (head != null) {
            //先将当前节点的next节点保存，后面要用，防止丢失
            next = head.next;
            //单链表需要操作一个指针，双向链表需要操作两个指针
            head.next = pre;
            head.last = next;
            //pre和head往后跳
            //把当前节点的引用赋给pre，下次遍历使用
            pre = head;
            //当前节点的next节点下次遍历时就变成了head节点
            head = next;
        }
        //当head==null时，就说明已经遍历完了，遍历的最后一步pre已经变成了新的头节点
        return pre;
    }

    public static Node testReverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int N = list.size();
        for (int i = 1; i < N; i++) {
            list.get(i).next = list.get(i - 1);
        }
        return list.get(N - 1);
    }

    public static DoubleNode testReverseDoubleList(DoubleNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<DoubleNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        DoubleNode pre = list.get(0);
        int N = list.size();
        for (int i = 1; i < N; i++) {
            DoubleNode cur = list.get(i);
            cur.last = null;
            cur.next = pre;
            pre.last = cur;
            pre = cur;
        }
        return list.get(N - 1);
    }

    public static Node generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        Node head = new Node((int) (Math.random() * (value + 1)));
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int) (Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    public static DoubleNode generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        DoubleNode head = new DoubleNode((int) (Math.random() * (value + 1)));
        DoubleNode pre = head;
        while (size != 0) {
            DoubleNode cur = new DoubleNode((int) (Math.random() * (value + 1)));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            size--;
        }
        return head;
    }

    // 要求无环，有环别用这个函数
    public static boolean checkLinkedListEqual(Node head1, Node head2) {
        while (head1 != null && head2 != null) {
            if (head1.value != head2.value) {
                return false;
            }
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1 == null && head2 == null;
    }

    // 要求无环，有环别用这个函数
    public static boolean checkDoubleListEqual(DoubleNode head1, DoubleNode head2) {
        boolean null1 = head1 == null;
        boolean null2 = head2 == null;
        if (null1 && null2) {
            return true;
        }
        if (null1 ^ null2) {
            return false;
        }
        if (head1.last != null || head2.last != null) {
            return false;
        }
        DoubleNode end1 = null;
        DoubleNode end2 = null;
        while (head1 != null && head2 != null) {
            if (head1.value != head2.value) {
                return false;
            }
            end1 = head1;
            end2 = head2;
            head1 = head1.next;
            head2 = head2.next;
        }
        if (head1 != null || head2 != null) {
            return false;
        }
        while (end1 != null && end2 != null) {
            if (end1.value != end2.value) {
                return false;
            }
            end1 = end1.last;
            end2 = end2.last;
        }
        return end1 == null && end2 == null;
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node node1 = generateRandomLinkedList(len, value);
            Node reverse1 = reverseLinkedList(node1);
            Node back1 = testReverseLinkedList(reverse1);
            if (!checkLinkedListEqual(node1, back1)) {
                System.out.println("oops!");
                break;
            }
            DoubleNode node2 = generateRandomDoubleList(len, value);
            DoubleNode reverse2 = reverseDoubleList(node2);
            DoubleNode back2 = testReverseDoubleList(reverse2);
            if (!checkDoubleListEqual(node2, back2)) {
                System.out.println("oops!");
                break;
            }
        }
        System.out.println("finish!");

    }
}
