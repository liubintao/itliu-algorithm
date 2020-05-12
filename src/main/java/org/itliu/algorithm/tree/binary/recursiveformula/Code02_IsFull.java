package org.itliu.algorithm.tree.binary.recursiveformula;

/**
 * @desc 是否是满二叉树
 * <p>
 * 定义：一棵深度为k，且有2^k-1个节点的树是满二叉树。
 * 另一种定义：除了叶结点外每一个结点都有左右子叶且叶子结点都处在最底层的二叉树。
 * @auther itliu
 * @date 2020/5/12
 */
public class Code02_IsFull {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 暴力解：根据定义深度为k，共有2^k-1个节点为思路，求出高度和节点数判断节点数是不是==2^height-1
     *
     * @param head 头节点
     * @return 是否是满二叉树
     */
    public static boolean isFull(Node head) {
        if (head == null) {
            return true;
        }
        int height = height(head);
        int nodes = nodes(head);
        return (1 << height) - 1 == nodes;
    }

    /**
     * 求数的高度
     */
    private static int height(Node head) {
        if (head == null) {
            return 0;
        }
        //左树和右树高度大的+1即为父节点的高度
        return Math.max(height(head.left), height(head.right)) + 1;
    }

    /**
     * 求数的节点数
     */
    private static int nodes(Node head) {
        if (head == null) {
            return 0;
        }
        //左树节点数+右树节点数+当前节点
        return nodes(head.left) + nodes(head.right) + 1;
    }


    //二叉树递归套路求解
    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }

        Info all = process(head);
        return (1 << all.height) - 1 == all.nodes;
    }

    private static class Info {
        public int height;
        public int nodes;

        public Info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    private static Info process(Node X) {
        if (X == null) {
            return new Info(0, 0);
        }
        //左树给我信息
        Info leftInfo = process(X.left);
        //右树给我信息
        Info rightInfo = process(X.right);
        //构建我自己的Info
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info(height, nodes);
    }

    //对数器-生成随机二叉树
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        //给一个随机的终止条件
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        //构建树
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    private static void print(Node head) {

    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull(head) != isFull2(head)) {
                System.out.println("Oops!");
//                isFull(head);
//                isFull2(head);
            }
        }
        System.out.println("finish!");
    }
}
