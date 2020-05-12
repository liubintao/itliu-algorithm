package org.itliu.algorithm.tree.binary.recursiveformula;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 是否是二叉搜索树
 * <p>
 * 二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树：
 * 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * 它的左、右子树也分别为二叉排序树。
 * <p>
 * 它既有链表的快速插入与删除操作的特点，又有数组快速查找的优势；
 * 所以应用十分广泛，例如在文件系统和数据库系统一般会采用这种数据结构进行高效率的排序与检索操作。
 * @auther itliu
 * @date 2020/5/12
 */
public class Code03_IsBST {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //暴力求解-借助二叉树的中序遍历
    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        List<Node> arr = new ArrayList<>();
        //中序遍历后放入list，左中右的顺序放入，arr[i] < arr[i+1]
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
    }

    private static void in(Node X, List<Node> arr) {
        if (X == null) {
            return;
        }
        in(X.left, arr);
        arr.add(X);
        in(X.right, arr);
    }

    //二叉树的递归套路求解
    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        //分析可能性
        /**
         * 1.与X有关
         *   左树->是搜索树
         *   右树->是搜索树
         *   左树最大值<X.value<右树最小值
         * 2.与X无关
         *   左树->是搜索树，右树->是搜索树，判断左树最大值<X.value<右树最小值
         *   左树->不是搜索树，右树->是搜索树，不是搜索树
         *   左树->是搜索树，右树->不是搜索树，不是搜索树
         *
         * 总结：递归返回信息，1.是不是搜索树 2.最小值 3.最大值
         */
        Info info = process(head);
        return info.isBST;
    }

    //左右子树得给我什么样的信息
    private static class Info {
        //子树上的最小值，用于判断右树最小值>头结点值
        public int min;
        //子树上的最大值，用于判断左树最大值<头结点值
        public int max;
        //子树是不是搜索树
        public boolean isBST;

        public Info(int min, int max, boolean isBST) {
            this.min = min;
            this.max = max;
            this.isBST = isBST;
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

        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
        }

        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
        }

        boolean isBST = false;

        if (
                (leftInfo == null ? true : (leftInfo.isBST && leftInfo.max < X.value))
                        &&
                        (rightInfo == null ? true : (rightInfo.isBST && rightInfo.max > X.value))
        ) {
            isBST = true;
        }

        return new Info(min, max, isBST);
    }

    //对数器
    private static Node generateRandomBST(int maxLevel, int maxValue) {
        return gererate(1, maxLevel, maxValue);
    }

    /**
     * 随机生成二叉树
     *
     * @param level    当前层
     * @param maxLevel 最大层
     * @param maxValue 最大值
     * @return 头节点
     */
    private static Node gererate(int level, int maxLevel, int maxValue) {
        //随机终止条件
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) Math.random() * maxValue);
        head.left = gererate(level + 1, maxLevel, maxValue);
        head.right = gererate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
