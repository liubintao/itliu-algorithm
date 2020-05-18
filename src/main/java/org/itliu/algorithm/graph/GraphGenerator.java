package org.itliu.algorithm.graph;

/**
 * @desc 生成图/将其他图结构转成自己的图结构
 * @auther itliu
 * @date 2020/5/18
 */
public class GraphGenerator {

    // matrix 所有的边
    // N*3 的矩阵
    // [weight, from节点上面的值，to节点上面的值]
    public static Graph createGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // matrix[0][0], matrix[0][1]  matrix[0][2]构成一条边
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];

            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }

            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }

            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);

            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.out++;
            toNode.in++;

            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);

            graph.edges.add(edge);
        }
        return graph;
    }
}
