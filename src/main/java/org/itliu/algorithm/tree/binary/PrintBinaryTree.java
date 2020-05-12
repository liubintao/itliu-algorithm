package org.itliu.algorithm.tree.binary;

/**
 * @desc: 设计一个打印整棵树的打印函数，横向打印，打印之后的数顺时针旋转90度即为正常状态下的树
 * @author: itliu
 * @date: 2020/4/29 16:29
 * @version: 1.0
 */
public class PrintBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void printTree(Node head) {
        System.out.println("Binary Tree：");
        printInOrder(head, 1, "H", 17);
        System.out.println();
    }

    /**
     * 根据树的高度计算每个节点前面的空格，每个节点填充到len长度
     *
     * @param head   头节点
     * @param height 树高度
     * @param to     树节点标志H-head,v-右子树节点,^-左子树节点
     * @param len    每个节点需要填充的长度
     */
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        //头节点两侧使用头标志包裹
        String val = to + head.value + to;
        //计算头节点前面应该填充的空格
        int lenM = val.length();
        //节点前面到当前树的高度之间应该填充多少空格
        int lenL = (len - lenM) / 2;
        //节点后面到下级节点之前应该填充多少空格
        int lenR = len - lenL - lenM;
        //当前节点前后用空格包裹
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    private static String getSpace(int num) {
        String space = " ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(space);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);

    }
}
