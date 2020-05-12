package org.itliu.algorithm.tree.tire;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: itliu
 * @date: 2020/4/26 21:38
 * @version: 1.0
 */
public class TrieTree {

    public static class Node1 {
        //有多少条路经过此节点
        public int pass;
        //有多少条路以此节点作为结尾
        public int end;
        //有多少条支路
        public Node1[] nexts;

        public Node1() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new Node1[26];
        }
    }

    public static class Trie1 {

        public Node1 root;

        public Trie1() {
            this.root = new Node1();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            Node1 node = root;
            node.pass++;

            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node1();
                }
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            Node1 node = root;
            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        public int prefixNumber(String word) {
            if (word == null) {
                return 0;
            }

            Node1 node = root;
            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                Node1 node = root;
                node.pass--;
                char[] str = word.toCharArray();
                for (int i = 0; i < str.length; i++) {
                    int index = str[i] - 'a';
                    if (--node.nexts[index].pass == 0) {
                        node.nexts[index] = null;
                        return;
                    }
                    node = node.nexts[index];
                }
                node.end--;
            }
        }
    }

    public static class Node2 {
        //有多少条路经过此节点
        public int pass;
        //有多少条路以此节点作为结尾
        public int end;
        //有多少条支路
        public Map<Integer, Node2> nexts;

        public Node2() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new HashMap<>();
        }
    }

    public static class Trie2 {

        public Node2 root;

        public Trie2() {
            this.root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            Node2 node = root;
            node.pass++;

            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = (int) str[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }

            Node2 node = root;
            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = (int) str[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        public int prefixNumber(String word) {
            if (word == null) {
                return 0;
            }

            Node2 node = root;
            char[] str = word.toCharArray();
            for (int i = 0; i < str.length; i++) {
                int index = (int) str[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                Node2 node = root;
                node.pass--;
                char[] str = word.toCharArray();
                for (int i = 0; i < str.length; i++) {
                    int index = (int) str[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }
    }

    public static class Right {
        private Map<String, Integer> map;

        public Right() {
            this.map = new HashMap<>();
        }

        public void insert(String word) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }

        public int search(String word) {
            return map.getOrDefault(word, 0);
        }

        public int prefixNumber(String word) {
            int count = 0;
            for (String cur :
                    map.keySet()) {
                if (cur.startsWith(word)) {
                    count += map.get(cur);
                }
            }
            return count;
        }

        public void delete(String word) {
            if (word == null) {
                return;
            }

            if (map.get(word) != null && map.get(word) > 1) {
                map.put(word, map.get(word) - 1);
            } else {
                map.remove(word);
            }
        }
    }


    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
