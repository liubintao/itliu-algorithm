package org.itliu.algorithm.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @desc 从图中任一node出发，图的宽度优先遍历，Breadth first traversal
 * @auther itliu
 * @date 2020/5/15
 */
public class Code02_BFS {

    public static void bfs(Node node) {
        if (node == null) {
            return;
        }

        //宽度优先用队列
        Queue<Node> queue = new LinkedList<>();
        //遍历过程中每个点只走一遍，防止环形图
        Set<Node> set = new HashSet<>();
        //先将发现的第一个节点加入队列
        queue.add(node);

        while (!queue.isEmpty()) {
            //弹出打印
            Node cur = queue.poll();
            System.out.println(cur.value);
            //将直接节点依次加入队列
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
}
