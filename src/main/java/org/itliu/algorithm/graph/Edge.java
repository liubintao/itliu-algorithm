package org.itliu.algorithm.graph;

/**
 * @desc 图中两个点之间的边结构
 * @auther itliu
 * @date 2020/5/15
 */
public class Edge {
    //权重，可以是int，也可以是包含int的复杂结构
    public int weight;
    public Node<?> from;
    public Node<?> to;

    public Edge(int weight, Node<?> from, Node<?> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
