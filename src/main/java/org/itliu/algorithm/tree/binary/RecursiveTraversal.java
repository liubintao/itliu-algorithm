package org.itliu.algorithm.tree.binary;

/**
 * @desc: 二叉树的递归先序、中序、后序遍历
 * <p>
 * 先序：任何子树的处理顺序都是，先头节点、再左子树、然后右子树
 * <p>
 * 中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树
 * <p>
 * 后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点
 * @author: itliu
 * @date: 2020/4/29 8:31
 * @version: 1.0
 */
public class RecursiveTraversal {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 无论是前序、中序、后序的二叉树遍历，都是以此函数为基础，只是打印语句加的地方不同，
     * 1.刚进入父节点的时候进入此函数 2.执行完left子树回到父节点的此函数 3.执行完right子树回到父节点的此函数
     * 一共会到达此函数三次，称为递归序
     *
     * @param head
     */
    public static void f(Node head) {
        if (head == null) {
            return;
        }

        f(head.left);
        f(head.right);
    }

    /**
     * 先序：任何子树的处理顺序都是，先头节点、再左子树、然后右子树
     */
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    /**
     * 中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树
     */
    public static void in(Node head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }

    /**
     * 后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点
     */
    public static void pos(Node head) {
        if (head == null) {
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");

    }
}
