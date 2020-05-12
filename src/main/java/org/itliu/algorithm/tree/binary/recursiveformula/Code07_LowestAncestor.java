package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.*;

/**
 * @desc 给定一颗二叉树的头结点head，和另外两个节点a和b，返回a和b的最低公共祖先
 * @auther itliu
 * @date 2020/5/12
 */
public class Code07_LowestAncestor {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //暴力求解-借助hashMap存储子节点->父节点对应关系
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        //其他节点也填充到parentMap里
        fillParentMap(head, parentMap);
        //顺着o1这条线将o1这条线上所有的父节点放进set里
        Set<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }

        //顺着o2这条线找o1那条线上的重合点
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }

        return cur;
    }

    private static void fillParentMap(Node head, Map<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    //二叉树递归套路求解
    public static Node lowestAncestor2(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        //分析可能性
        /**
         * O1和O2最初的交汇点在哪？
         * 1) 在左树上已经提前交汇了
         * 2) 在右树上已经提前交汇了
         * 3) 没有在左树或者右树上提前交汇，O1  O2 全了
         */

        //构造递归函数返回结果Info
        return process(head, o1, o2).ans;
    }

    private static class Info {
        public Node ans;
        public boolean findO1;
        public boolean findO2;

        public Info(Node ans, boolean findO1, boolean findO2) {
            this.ans = ans;
            this.findO1 = findO1;
            this.findO2 = findO2;
        }
    }

    private static Info process(Node X, Node o1, Node o2) {
        if (X == null) {
            return new Info(null, false, false);
        }
        Info leftInfo = process(X.left, o1, o2);
        Info rightInfo = process(X.right, o1, o2);
        boolean findO1 = X == o1 || leftInfo.findO1 || rightInfo.findO1;
        boolean findO2 = X == o2 || leftInfo.findO2 || rightInfo.findO2;

        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        }

        if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        }

        if (ans == null) {
            if (findO1 && findO2) {
                ans = X;
            }
        }

        return new Info(ans, findO1, findO2);
    }

    //对数器
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxLevel);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        //构造随机停止条件
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        Node head = new Node((int) Math.random() * maxValue);
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    //随机选取一个节点
    private static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        List<Node> arr = new ArrayList<>();
        fillPreList(head, arr);

        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    private static void fillPreList(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
