package org.itliu.algorithm.tree.binary;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @desc: 二叉树的序列化和反序列化
 * <p>
 * 1）可以用先序或者中序或者后序或者按层遍历，来实现二叉树的序列化
 * 2）用了什么方式序列化，就用什么样的方式反序列化
 * @author: itliu
 * @date: 2020/4/29 12:17
 * @version: 1.0
 */
public class SerializeAndReconstructTree {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
            return;
        }
        ans.add(String.valueOf(head.value));
        pres(head.left, ans);
        pres(head.right, ans);
    }

    public static Node buildByPreQueue(Queue<String> preList) {
        if (preList == null || preList.isEmpty()) {
            return null;
        }

        return preb(preList);
    }

    private static Node preb(Queue<String> preList) {
        String value = preList.poll();
        if (value == null) {
            return null;
        }

        Node head = new Node(Integer.valueOf(value));
        head.left = preb(preList);
        head.right = preb(preList);
        return head;
    }

    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);

            while (!queue.isEmpty()) {
                head = queue.poll();

                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    ans.add(null);
                }

                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    public static Node buildByLevelQueue(Queue<String> ans) {
        if (ans == null || ans.isEmpty()) {
            return null;
        }

        //构建head节点
        Node head = generateNode(ans.poll());
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        Node node = null;
        while (!ans.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(ans.poll());
            node.right = generateNode(ans.poll());

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }

    private static Node generateNode(String val) {
        if (val == null) {
            return null;
        }

        return new Node(Integer.valueOf(val));
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

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {
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

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
