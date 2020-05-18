package org.itliu.algorithm.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 图中每个点的结构
 * @auther itliu
 * @date 2020/5/15
 */
public class Node {
    public Integer value;
    //入度
    public int in;
    //出度
    public int out;
    //直连的后继节点
    public List<Node> nexts;
    //直接的边
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
