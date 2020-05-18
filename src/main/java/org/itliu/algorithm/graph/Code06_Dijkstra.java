package org.itliu.algorithm.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @desc Dijkstra算法
 * <p>
 * 1）Dijkstra算法必须指定一个源点
 * 2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
 * 3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
 * 4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
 * @auther itliu
 * @date 2020/5/16
 */
public class Code06_Dijkstra {

    public static Map<Node, Integer> dijkstra1(Node from) {
        //从head出发到所有点的最小距离
        // key : 从head出发到达key
        // value : 从head出发到达key的最小距离
        // 如果在表中，没有T的记录，含义是从head出发到T这个点的距离为正无穷
        Map<Node, Integer> distanceMap = new HashMap<>();
        //设置初始值，当前节点到当前节点的距离为0，其他节点到当前节点的距离为正无穷
        distanceMap.put(from, 0);

        // 已经求过距离的节点，存在selectedNodes中，以后再也不碰
        Set<Node> selectedNodes = new HashSet<>();

        //从距离表中拿出当前最短的距离节点，第一次拿到from
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);

        while (minNode != null) {
            //取出没有处理过的最短距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                //看看toNode在不在距离表中
                final Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    distanceMap.put(toNode,
                            Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }

            //到此为止,minNode已经处理完，以后就不要碰了
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(
            Map<Node, Integer> distanceMap,
            Set<Node> touchedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = distanceMap.get(node);
            if (!touchedNodes.contains(node)
                    && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static Map<Node, Integer> dijkstra2(Node head, int size) {
        //创建一个size大小的heap
        NodeHeap nodeHeap = new NodeHeap(size);
        //有了堆之后就得往里放节点，最开始只有head节点，那就把它放进去
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        Map<Node, Integer> result = new HashMap<>();

        while (!nodeHeap.isEmpty()) {
            //因为每次只处理最小距离的节点，处理完就不管它了，所以我们可以从堆中弹出元素保留处理后的结果即可
            NodeRecord nodeRecord = nodeHeap.pop();
            Node cur = nodeRecord.node;
            int distance = nodeRecord.distance;
            for (Edge edge : cur.edges) {
                //取最小距离的操作交给heap，我们每次只管加进去，然后pop
                nodeHeap.addOrUpdateOrIgnore(edge.to, distance + edge.weight);
            }
            result.put(cur, distance);
        }
        return result;
    }

    private static class NodeHeap {
        //实际的堆结构
        private Node[] nodes;
        // key 某一个node， value 上面堆中的位置
        private Map<Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap(int size) {
            this.nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            //在堆里就更新，不在堆里有两种情况：1、没进入过，放进去  2、放进去过又弹出了，忽略，证明已经处理了
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        private NodeRecord pop() {
            NodeRecord record = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);

            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return record;
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (index < size) {
                //左右孩子谁小
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                //左右孩子和父节点谁小
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }

                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }


        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index])
                    < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void swap(int from, int to) {
            heapIndexMap.put(nodes[to], from);
            heapIndexMap.put(nodes[from], to);
            Node tmp = nodes[to];
            nodes[to] = nodes[from];
            nodes[from] = tmp;
        }
    }

    private static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
