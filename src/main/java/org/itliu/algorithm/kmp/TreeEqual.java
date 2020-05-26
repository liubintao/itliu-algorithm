package org.itliu.algorithm.kmp;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 给定两棵二叉树的头节点head1和head2  想知道head1中是否有某个子树的结构和head2完全一样
 * @auther itliu
 * @date 2020/5/26
 */
public class TreeEqual {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    //暴力求解
    private static boolean containsTree1(Node big, Node small) {
        //任何一棵树都包含空树
        if (small == null) {
            return true;
        }
        //来到这一步证明small不空，任何一颗空树都不包含不空的树
        if (big == null) {
            return false;
        }

        //如果包含这棵树就返回
        if (isSameValueStructure(big, small)) {
            return true;
        }

        //如果big当前子树结构与small不一致，那就从左树和右树去判断
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    private static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }

        if (head1 != null && head2 == null) {
            return false;
        }

        if (head1 == null && head2 == null) {
            return true;
        }

        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    //KMP算法求解
    private static boolean containsTree2(Node big, Node small) {
        //任何一棵树都包含空树
        if (small == null) {
            return true;
        }
        //来到这一步证明small不空，任何一颗空树都不包含不空的树
        if (big == null) {
            return false;
        }

        //生成两个数组，但是不知道树有多少个节点，所以先先序遍历生成List再转为数组
        List<String> b = preSerial(big);
        List<String> s = preSerial(small);

        //生成数组
        String[] str = new String[b.size()];
        for (int i = 0; i < b.size(); i++) {
            str[i] = b.get(i);
        }

        String[] match = new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            match[i] = s.get(i);
        }

        //有了KMP算法中需要的两个参数str和match，调用kmp算法即可
        return getIndexOf(str, match) != -1;
    }

    private static List<String> preSerial(Node head) {
        List<String> ans = new ArrayList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(Node head, List<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {

            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    private static int getIndexOf(String[] str, String[] match) {
        if (str == null || match == null || str.length < 1 || str.length < match.length) {
            return -1;
        }

        //生成next数组
        int[] next = getNextArray(match);
        //给两个移动指针
        int x = 0;
        int y = 0;

        //x没越界 && y没越界
        while (x < str.length && y < match.length) {
            if (isEqual(str[x], match[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == match.length ? x - y : -1;
    }

    private static int[] getNextArray(String[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }

        int[] res = new int[match.length];
        res[0] = -1;
        res[1] = 0;

        int cn = 0;
        int i = 2;
        while (i < res.length) {
            if (isEqual(match[i-1], match[cn])) {
                res[i++] = ++cn;
            } else if (cn > 0) {
                cn = res[cn];
            } else {
                res[i++] = 0;
            }
        }
        return res;
    }

    private static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
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
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
