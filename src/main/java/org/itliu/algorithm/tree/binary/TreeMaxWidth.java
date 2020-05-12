package org.itliu.algorithm.tree.binary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @desc: 求二叉树的最大宽度：可以通过设置flag变量的方式，来发现某一层的结束
 * @author: itliu
 * @date: 2020/4/29 11:02
 * @version: 1.0
 */
public class TreeMaxWidth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        Map<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);

        int curLevel = 1;
        int curLevelNodes = 0;
        int max = 0;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            //获取当前节点所在层
            Integer curNodeLevel = levelMap.get(cur);

            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }

            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }

            //还在同一层
            if (curLevel == curNodeLevel) {
                //结算当前层的节点数
                curLevelNodes++;
            } else {
                //不在同一层了，结算上一层的节点数
                max = Math.max(max, curLevelNodes);
                //当前层+1
                curLevel++;
                //当前层节点数清零，用于下一层结算，
                // 此处设置成1，是因为使用map的机制是到了新层才能发现不在同一层，此时已经发现了一个节点
                curLevelNodes = 1;

            }
        }
        //结算最后一层的节点数
        max = Math.max(max, curLevelNodes);
        return max;
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

    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        Node curEnd = head;//当前层结束节点
        Node nextEnd = null;//下一层结束节点，到达此节点时结算

        int max = 0;
        int curLevelNodes = 0;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            /**
             * 往队列里add时处理nextEnd，只有nextEnd从队列弹出时才进行结算
             */
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }

            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }

            curLevelNodes++;

            //当前层该结算了
            if (cur == curEnd) {
                //结算
                max = Math.max(max, curLevelNodes);
                //清零
                curLevelNodes = 0;
                //为了下次结算
                curEnd = nextEnd;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }
}
