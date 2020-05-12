package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的大小
 * @author: itliu
 * @date: 2020/4/30 9:52
 * @version: 1.0
 */
public class Code04_MaxSubBSTSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        List<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            //利用搜索树左树<头<右树&&中序左头右顺序，只要头<=左树||右树<=头即可判定不是搜索树
            //头<=左树||右树<=头又利用中序左头右顺序特性，在数组中只要右侧<=左侧就代表不是搜索树
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }

    public static class Info {
        public boolean isAllBST;
        public int maxSubBSTSize;
        public int max;
        public int min;

        public Info(boolean isAllBST, int maxSubBSTSize, int max, int min) {
            this.isAllBST = isAllBST;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    public static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }

        return process(head).maxSubBSTSize;
    }

    private static Info process(Node X) {
        if (X == null) {
            return null;
        }

        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        int max = X.value;
        int min = X.value;

        //默认为X无关情况
        int maxSubBSTSize = 0;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            maxSubBSTSize = Math.max(maxSubBSTSize, leftInfo.maxSubBSTSize);
        }
        //默认为X无关情况
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
        }
        //默认为X无关情况
        boolean isAllBST = false;


        //如果是X有关情况呢
        if (
            //1.左树得是搜索树&&左树max < X.value
                (leftInfo == null ? true : (leftInfo.isAllBST && leftInfo.max < X.value))
                        &&
                        //2.右树得是搜索树右树min > X.value
                        (rightInfo == null ? true : (rightInfo.isAllBST && rightInfo.min > X.value))
        ) {
            isAllBST = true;
            maxSubBSTSize =
                    (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                            +
                            (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                            +
                            1;
        }

        return new Info(isAllBST, maxSubBSTSize, max, min);
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
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
