package org.itliu.algorithm.linkedList;

import java.util.Stack;

/**
 * @desc: 是否是回文串
 * @author: itliu
 * @date: 2020/4/28 11:27
 * @version: 1.0
 */
public class IsPalindromeList {

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    //need a extra space
    public static boolean isPalindrome1(Node head) {
        //base case
        if (head == null) {
            return true;
        }

        Stack<Integer> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur.value);
            cur = cur.next;
        }

        while (head != null) {
            if (head.value != stack.pop()) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //need n/2 extra space
    public static boolean isPalindrome2(Node head) {
        //base case
        /**
         * base case
         * 1.头结点为null，true
         * 2.只有一个节点，true
         */
        if (head == null || head.next == null) {
            return true;
        }

        /**
         * 设置起点
         * 1.只有两个节点，cur指向head，right指向下中点，将right压栈
         *   0    1
         *   c    r
         * 2.只有三个节点，cur指向head，right指向中点，从right开始压栈
         *   0    1    2
         *   c    r
         * 3.只有四个节点，cur越过right每次走两步，right停留在下中点，从right开始压栈
         *   0    1    2   3
         *             r   c
         * 4.只有五个节点，cur指向head，right停留在中点，从right开始压栈
         *   0    1    2   3   4
         *             r   c
         */
        Node right = head.next;
        Node cur = head;

        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }


        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        while (!stack.isEmpty()) {
            if (stack.pop().value != head.value) {
                return false;
            }
            head = head.next;
        }

        return true;
    }

    // need O(1) extra space
    public static boolean isPalindrome3(Node head) {
        //base case
        if (head == null || head.next == null) {
            return true;
        }

        /**
         * step1：找中点
         */
        Node n1 = head;//慢指针
        Node n2 = head;//快指针
        while (n2.next != null && n2.next.next != null) { //find mid node
            n1 = n1.next;//n1 -> mid
            n2 = n2.next.next; //n2 -> end
        }

        //n1 -> mid/up mid
        /**
         * step2:反转右半部分
         */
        n2 = n1.next;//right part first node
        n1.next = null;//mid.next -> null
        Node n3 = null;
        while (n2 != null) { //right part convert
            //找到要反转的下个节点
            n3 = n2.next;//n3 -> save the last node
            //当前节点的next指针指向上一个节点
            n2.next = n1;// next of right node convert
            //上一个节点往后走
            n1 = n2;//n1 move
            //要反转的下个节点往后走
            n2 = n3;//n2 move
        }

//        System.out.println("--------------");
//        printLinkedList(head);
//        printLinkedList(n1);
//        System.out.println("--------------");
        n3 = n1;//n3 -> save last node
        n2 = head;//n2 -> left part first node
        /**
         * step3:L>>>mid<<<R，左右两指针判断回文
         */
        boolean res = true;
        while (n2 != null && n1 != null) {// check palindrome
            if (n2.value != n1.value) {
                res = false;
                break;//注意：不能return，后面还要将反转的右半部分还原
            }
            n2 = n2.next;//left to mid
            n1 = n1.next;//right to mid
        }
        /**
         * step4:还原右半部分，又一次反转
         */
        //n1设置为从最右指针开始，此时n3在最右
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) {//convert list
            //拿到下一个要反转的节点
            n2 = n1.next;
            //将下一个要反转的节点指针向后指，当前后面是右侧的n3
            n1.next = n3;
            //上一步消耗了n3，n3要跟着往前走一步
            n3 = n1;
            //要反转的节点往前走
            n1 = n2;
        }
        return res;
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}
