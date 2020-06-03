package org.itliu.algorithm.morris;

/**
 * @desc Morris遍历
 *       一种遍历二叉树的方式，并且时间复杂度O(N)，额外空间复杂度O(1)
 *       通过利用原树中大量空闲指针的方式，达到节省空间的目的
 * @auther itliu
 * @date 2020/6/3
 */
public class MorrisTraversal {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static void morris(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head; //cur先来到头节点
        Node mostRight = null;
        //cur不为空就继续，为空就终止，注意作用域内的代码中cur只有两次变化，向左cur = cur.left; 向右cur = cur.right;
        //简单来说，cur不断向左或向右移动，一旦为空就跳出来
        while (cur != null) {
            //cur有没有左树
            mostRight = cur.left;
            if (mostRight != null) { //有左树的情况下
                //找到cur左树上，真实的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                //从while中出来，意味着mostRight一定是cur左树上的最右节点
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue; //回到外面的大while
                } else { //mostRight.right != null -> mostRight.right == cur
                    mostRight.right = null;
                    //下一步就出了if (mostRight != null) 语句块了，会执行cur = cur.right;
                }
            }
            //cur没有左树，向右移动
            cur = cur.right;
        }
    }

    private static void morrisIn(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        Node mostRight = null;

        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            //要往右移动了，直接打印
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    private static void morrisPre(Node head) {
        if (head == null) {
            return;
        }

        //头左右，有左树的头结点会来到两次，第一次到来的时候打印
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
               while (mostRight.right != null && mostRight.right != cur) {
                   mostRight = mostRight.right;
               }

               if (mostRight.right == null) {//第一次到来
                   mostRight.right = cur;
                   System.out.print(cur.value + " ");
                   cur = cur.left;
                   continue;
               } else {
                   mostRight.right = null;
               }
            } else { // 没有左树的直接打印
                System.out.print(cur.value + " ");
            }
            //没有左树直接向右移动
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 打印时机放到能回到自己两次且第二次回到自己的时候，但是不是打印它自己，是打印左树的右边界，逆序打印，
     * 所有过程完成之后单独打印整棵树的右边界，也是逆序打印
     * @param head
     */
    private static void morrisPos(Node head) {
        if (head == null) {
            return;
        }

        //头左右，有左树的头结点会来到两次，第一次到来的时候打印
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {//第一次到来
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    //逆序打印左边界
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        //逆序打印整棵树的右边界
        printEdge(head);
        System.out.println();
    }

    private static void printEdge(Node head) {
        //逆序 -> 打印 -> 恢复
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    private static Node reverseEdge(Node cur) {
        Node pre = null;
        Node next = null;
        while (cur != null) {
            next = cur.right;//先拿到后面的值
            cur.right = pre;//后面的值重新指向
            pre = cur;//前一个值重新赋值，下次遍历使用
            cur = next;//当前值重新赋值，下次遍历使用
        }

        return pre;
    }

    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);
        printTree(head);

    }
}
