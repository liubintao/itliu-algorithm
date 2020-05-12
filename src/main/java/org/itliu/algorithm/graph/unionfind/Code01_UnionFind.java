package org.itliu.algorithm.graph.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @desc:
 * @author: itliu
 * @date: 2020/5/9 16:44
 * @version: 1.0
 */
public class Code01_UnionFind {

    /**
     * 单个样本数据
     * @param <V> 样本数据类型
     */
    public static class Node<V>{
        public V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public static class UnionSet<V> {
        //初始时每个值代表的节点
        public Map<V, Node<V>> nodes;
        //每个节点对应的parent节点
        public Map<Node<V>,Node<V>> parents;
        //总共的代表点
        public Map<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            for (V v : values) {
                Node<V> node = new Node<>(v);
                nodes.put(v, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }

            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }

            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) ||!nodes.containsKey(b)) {
                return;
            }

            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));

            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);

                if (aSize > bSize) {
                    parents.put(bHead, aHead);
                    sizeMap.put(aHead, aSize + bSize);
                    sizeMap.remove(bHead);
                } else {
                    parents.put(aHead, bHead);
                    sizeMap.put(bHead, aSize + bSize);
                    sizeMap.remove(aHead);
                }
            }
        }
    }
}
