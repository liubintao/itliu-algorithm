package org.itliu.algorithm.graph;

import java.util.*;

/**
 * @desc 图结构，说白了就是一堆点和一堆边
 * @auther itliu
 * @date 2020/5/15
 */
public class Graph {
    //每个点的唯一标识为key，可以是编号，点为value
    public Map<Integer, Node> nodes;
    public Set<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
