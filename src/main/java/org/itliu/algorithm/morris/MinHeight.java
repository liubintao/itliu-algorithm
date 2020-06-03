package org.itliu.algorithm.morris;

/**
 * @desc 给定一棵二叉树的头节点head  求以head为头的树中，最小深度是多少？
 * @auther itliu
 * @date 2020/6/3
 */
public class MinHeight {
    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //二叉树递归套路求解
    private static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return p(head);
    }

    private static int p(Node X) {
        if (X.left == null && X.right == null) {
            return 1;
        }

        // 左右子树起码有一个不为空
        int leftH = Integer.MAX_VALUE;
        if (X.left != null) {
            leftH = p(X.left);
        }

        int rightH = Integer.MAX_VALUE;
        if (X.right != null) {
            rightH = p(X.right);
        }
        return 1 + Math.min(leftH, rightH);
    }

    //Morris遍历求解
    private static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }

        int minHeight = Integer.MAX_VALUE;

        Node cur = head;
        Node mostRight = null;
        int curLevel = 0;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                int rightBoardHeight = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardHeight++;
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) { //第一次来到
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { //第二次来到
                    //判断是否叶子节点
                    if (mostRight.left == null) {//左树的右边界是叶子节点，那就抓一下高度
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardHeight;
                    mostRight.right = null;
                }
            } else { //只有一次到达
                curLevel++;
            }
            cur = cur.right;
        }

        //抓整棵树的最右节点的高度
        int finalRight = 1;
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }

        return minHeight;
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
        int treeLevel = 10;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println(ans1 + " " + ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
