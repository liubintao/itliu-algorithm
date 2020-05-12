package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @desc 是否是完全二叉树
 * <p>
 * 若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，
 * 第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。
 * @auther itliu
 * @date 2020/5/12
 */
public class Code06_IsCBT {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //暴力求解-宽度优先遍历
    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node left = null;
        Node right = null;

        while (!queue.isEmpty()) {
            Node X = queue.poll();
            left = X.left;
            right = X.right;

            //如果已经遇到过左右两个孩子不双全的节点，并且当前节点不是叶子节点/左树空右树不空
            //1.当前节点的左节点!=null
            //2.当前节点的右节点!=null
            //上面两条说明当前节点不是叶子节点
            //左树空右树不空说明已经不是完全二叉树了
            if ((leaf && (left != null || right != null)) || (left == null && right != null)) {
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }

            if (left == null || right == null) {
                leaf = true;
            }
        }
        return true;
    }

    //二叉树递归套路求解
    public static boolean isCBT2(Node head) {
        if (head == null) {
            return true;
        }
        //一.分析可能性，将可能性中的信息带入递归函数
        /**
         * 1.是满二叉树
         * 2.左树->完全二叉树；右树->满二叉树；左高-右高=1
         * 3.左树->满二叉树；右树->满二叉树；左高-右高=1
         * 4.左树->满二叉树；右树->完全二叉树；左高=右高
         */
        //二.对每一棵子树，应该返回什么信息
        /**
         * 1.是否是满二叉树
         * 2.是否是完全二叉树
         * 3.高度
         */
        return process(head).isCBT;
    }

    private static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    private static Info process(Node X) {
        if (X == null) {
            return new Info(true, true, 0);
        }

        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        /**
         * 1.是满二叉树
         * 2.左树->完全二叉树；右树->满二叉树；左高-右高=1
         * 3.左树->满二叉树；右树->满二叉树；左高-右高=1
         * 4.左树->满二叉树；右树->完全二叉树；左高=右高
         */
        //高度最好求，先求高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;

        //是满二叉树
        if (isFull) {
            isCBT = true;
        } else {
            //左树->完全二叉树；右树->满二叉树；左高-右高=1
            if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                isCBT = true;
            }
            //左树->满二叉树；右树->满二叉树；左高-右高=1
            if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                isCBT = true;
            }
            //左树->满二叉树；右树->完全二叉树；左高=右高
            if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                isCBT = true;
            }
        }

        return new Info(isFull, isCBT, height);
    }
    
    //对数器
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        //随机终止条件
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) Math.random() * maxValue);
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
