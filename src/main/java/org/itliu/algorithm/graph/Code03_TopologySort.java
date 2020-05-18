package org.itliu.algorithm.graph;

import java.util.*;

/**
 * @desc 图的拓扑排序算法
 * 1）在图中找到所有入度为0的点输出
 * 2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
 * 3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
 * 要求：有向图且其中没有环
 * 应用：事件安排、编译顺序
 * @auther itliu
 * @date 2020/5/15
 */
public class Code03_TopologySort {

    public List<Node> sortedTopology(Graph graph) {
        // key：某一个node
        // value：剩余的入度
        Map<Node, Integer> inMap = new HashMap<>();
        // 剩余入度为0的点，才能进这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();

        for (Node cur : graph.nodes.values()) {
            inMap.put(cur, cur.in);
            if (cur.in == 0) {
                zeroInQueue.add(cur);
            }
        }

        // 拓扑排序的结果，依次加入result
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            final Node<Integer> node = zeroInQueue.poll();
            result.add(node);

            for (Node next : node.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }

        return result;
    }
}
