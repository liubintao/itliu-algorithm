package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 给定一棵二叉树的头节点head， 返回这颗二叉树中最大的二叉搜索子树的头节点
 * @auther itliu
 * @date 2020/5/12
 */
public class Code05_MaxSubBSTHead {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    //当前head节点为头的二叉搜索树的大小
    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        //中序遍历
        List<Node> arr = new ArrayList<>();
        in(head, arr);

        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    private static void in(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }

        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    //暴力求解-借助中序遍历
    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }

        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    //二叉树递归套路求解
    public static Node maxSubBSTHead2(Node head) {
        if (head == null) {
            return null;
        }
        //分析可能性
        /**
         * 1.X有关情况
         *   1.左树得是搜索树&&左树max < X.value
         *   2.右树得是搜索树右树min > X.value
         * 2.X无关情况
         *   1.最大搜索树在左树
         *   2.最大搜索树在右树
         */
        return process(head).maxSubBSTHead;
    }

    private static class Info {
        public Node maxSubBSTHead;
        public int maxSubBSTSize;
        public int min;
        public int max;

        public Info(Node maxSubBSTHead, int maxSubBSTSize, int min, int max) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
            this.min = min;
            this.max = max;
        }
    }

    private static Info process(Node X) {
        if (X == null) {
            return null;
        }

        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        int min = X.value;
        int max = X.value;
        Node maxSubBSTHead = null;
        int maxSubBSTSize = 0;
        //X无关情况
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }

        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            //右树最大搜索子树节点数>左树最大搜索子树节点数
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }

        //X有关情况
        if (
                (leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
                &&
                (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))
        ) {
            maxSubBSTHead = X;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
        }
        return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 10;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
