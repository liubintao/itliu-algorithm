package org.itliu.algorithm.graph.unionfind;

import java.util.*;

/**
 * @desc 一个用户数组，每个用户有a,b,c三个属性，如果两个用户的任意a或b或c属性相同，则认为这是一个用户，求总共有几个用户
 * @auther itliu
 * @date 2020/5/15
 */
public class Code07_MergeUsers {

    private static class UnionSet<V> {

        private Map<V, Node<V>> nodes = new HashMap<>();
        private Map<Node<V>, Node<V>> parentMap = new HashMap<>();
        private Map<Node<V>, Integer> sizeMap = new HashMap<>();

        public UnionSet(List<V> list) {
            for (V v : list) {
                Node<V> node = new Node<>(v);
                nodes.put(v, node);
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private static class Node<V> {
            public V value;

            public Node(V value) {
                this.value = value;
            }
        }

        private Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            //代表点不是自己
            while (cur != parentMap.get(cur)) {
                path.push(cur);
                cur = parentMap.get(cur);
            }

            //将走过的路径扁平化
            while (!path.isEmpty()) {
                parentMap.put(path.pop(), cur);
            }

            return cur;
        }

        private boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }

            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        private void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return;
            }

            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));

            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);

                Node<V> big = aSize >= bSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;

                parentMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

        private int getSize() {
            return sizeMap.size();
        }
    }


    public static class User {
        public String a;
        public String b;
        public String c;

        public User(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    // (1,10,13) (2,10,37) (400,500,37)
    // 如果两个user，a字段一样、或者b字段一样、或者c字段一样，就认为是一个人
    // 请合并users，返回合并之后的用户数量
    public static int mergeUsers(List<User> users) {
        UnionSet<User> unionSet = new UnionSet<>(users);

        Map<String, User> aMap = new HashMap<>();
        Map<String, User> bMap = new HashMap<>();
        Map<String, User> cMap = new HashMap<>();

        for (User u : users) {
            if (aMap.containsKey(u.a)) {
                unionSet.union(u, aMap.get(u.a));
            } else {
                aMap.put(u.a, u);
            }

            if (bMap.containsKey(u.a)) {
                unionSet.union(u, bMap.get(u.a));
            } else {
                bMap.put(u.a, u);
            }

            if (cMap.containsKey(u.a)) {
                unionSet.union(u, cMap.get(u.a));
            } else {
                cMap.put(u.a, u);
            }
        }

        return unionSet.getSize();
    }

    public static void main(String[] args) {
        User u1 = new User("a", "b", "c");
        User u2 = new User("a", "d", "f");
        User u3 = new User("e", "f", "g");
        User u4 = new User("h", "h", "h");
//        User u5 = new User("a", "b", "c");
//        User u6 = new User("a", "b", "c");
//        User u7 = new User("a", "b", "c");
//        User u8 = new User("a", "b", "c");
        List<User> users = Arrays.asList(u1, u2, u3, u4);
        System.out.println(mergeUsers(users));
    }
}
