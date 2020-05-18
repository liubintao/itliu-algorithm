package org.itliu.algorithm.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @desc 图的深度优先遍历
 * @auther itliu
 * @date 2020/5/15
 */
public class Code02_DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();

        //加入就打印
        stack.add(node);
        set.add(node);
        //真正业务处理时替换成处理类
        System.out.println(node.value);

        while (!stack.isEmpty()) {
            final Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    //深度优先每次只加一个点，还是加入打印
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }
}
