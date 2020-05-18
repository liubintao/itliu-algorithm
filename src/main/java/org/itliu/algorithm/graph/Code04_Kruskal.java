package org.itliu.algorithm.graph;

import java.util.*;

/**
 * @desc 最小生成树算法之Kruskal
 * 1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2）当前的边要么进入最小生成树的集合，要么丢弃
 * 3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 5）考察完所有边之后，最小生成树的集合也得到了
 * @auther itliu
 * @date 2020/5/15
 */
//undirected graph only
public class Code04_Kruskal {

    private static class UnionFind {
        // key 某一个节点， value key节点往上的节点
        public Map<Node, Node> parentMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        public Map<Node, Integer> sizeMap;

        public UnionFind() {
            this.parentMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node<?>> nodes) {
            parentMap.clear();
            sizeMap.clear();

            for (Node node : nodes) {
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node n) {
            Stack<Node> path = new Stack<>();
            while (n != parentMap.get(n)) {
                path.add(n);
                n = parentMap.get(n);
            }

            while (!path.isEmpty()) {
                parentMap.put(path.pop(), n);
            }

            return n;
        }

        private boolean isSameSet(Node a, Node b) {
            Node aHead = findFather(a);
            Node bHead = findFather(b);

            return aHead == bHead;
        }

        private void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }

            Node aHead = findFather(a);
            Node bHead = findFather(b);

            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                if (aSetSize <= bSetSize) {
                    parentMap.put(aHead, bHead);
                    sizeMap.put(bHead, aSetSize + bSetSize);
                    sizeMap.remove(aHead);
                } else {
                    parentMap.put(bHead, aHead);
                    sizeMap.put(aHead, aSetSize + bSetSize);
                    sizeMap.remove(bHead);
                }
            }
        }
    }

    private static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());

        //把所有的边都放入最小堆
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());

        graph.edges.forEach(e -> { // 一共M条边
            queue.add(e); //O(logM)
        });

        Set<Edge> result = new HashSet<>();
        while (!queue.isEmpty()) {
            final Edge edge = queue.poll();
            if (!unionFind.isSameSet(edge.from, edge.to)) {// O(1)
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
