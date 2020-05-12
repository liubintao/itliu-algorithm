package org.itliu.algorithm.linkedList;

/**
 * @desc: 给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
 * 【要求】
 * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 * <p>
 * 此题包含三个小题：
 * 1.给你一个链表，返回入环的节点
 * 2.两个无环链表相交，返回第一个相交的节点
 * 3.两个有环的链表，找到相交的节点
 * @author: itliu
 * @date: 2020/4/28 18:08
 * @version: 1.0
 */
public class FindFirstIntersectNode {

    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }


    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        /**
         * step1:判断两个链表是否有环，有环返回入环节点，无环返回null
         */
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        /**
         * step2: 两种情况，1.两个链表都无环  2.两个链表都有环  注意：一个链表有环一个无环是不可能相交的
         *
         */
        if (loop1 == null && loop2 == null) {
            /**
             * 两个链表都无环，如果相交肯定后半部分重合，也就是说尾结点相等，如下图示例
             *          *
             *           *     *
             *            *   *
             *             * *
             *              *
             *              *
             *              *
             *              *
             *              *
             */
            return noLoop(head1, head2);
        }

        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }


    // 找到链表第一个入环节点，如果无环，返回null
   /* public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // n1 慢  n2 快
        Node n1 = head.next; // n1 -> slow
        Node n2 = head.next.next; // n2 -> fast
        while (n1 != n2) {
            if (n2.next == null || n2.next.next == null) {
                return null;
            }
            n2 = n2.next.next;
            n1 = n1.next;
        }
        n2 = head; // n2 -> walk again from head
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }*/
    // 找到链表第一个入环节点，如果无环，返回null
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        //快慢指针看是否有环
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            //没有环的情况
            if (fast.next == null || fast.next.next == null) {
                return null;
            }

            slow = slow.next;
            fast = fast.next.next;
        }

        fast = head;// fast -> walk again from head
        //如果有环，一定会在环的节点相遇
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    // 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
    private static Node noLoop(Node head1, Node head2) {
        //这里可以不用判断，主方法进来的时候已经判断了
        if (head1 == null || head2 == null) {
            return null;
        }

        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;//记录两个链表长度差
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }

        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }

        //没有重合部分，重合部分尾结点一定相等
        if (cur1 != cur2) {
            return null;
        }

        // n  :  链表1长度减去链表2长度的值
        cur1 = n > 0 ? head1 : head2;// 谁长，谁的头变成cur1
        cur2 = cur1 == head1 ? head2 : head1;// 谁短，谁的头变成cur2

        n = Math.abs(n);
        //较长的链表先走n步，保证走n步后两个链表一样长
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }

        //找相交节点，找到返回，找不到返回null
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        return cur1;
    }

    // 两个有环链表，返回第一个相交节点，如果不相交返回null
    private static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {

        Node cur1 = null;
        Node cur2 = null;

        //和无环相交结构类似，只是在重合部分的最后多了个环，入环前相交
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }

            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }

            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;

            n = Math.abs(n);

            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }

            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }

            return cur1;
        } else {
            //入环后相交
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
