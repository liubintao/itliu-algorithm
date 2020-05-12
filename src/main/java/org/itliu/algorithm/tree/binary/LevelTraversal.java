package org.itliu.algorithm.tree.binary;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @desc: 按层遍历
 * @author: itliu
 * @date: 2020/4/29 10:55
 * @version: 1.0
 */
public class LevelTraversal {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void level(Node head) {
        if (head != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                System.out.println(cur.value);

                if (cur.left != null) {
                    queue.add(cur.left);
                }

                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        level(head);
        System.out.println("========");
    }
}
