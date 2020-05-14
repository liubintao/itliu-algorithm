package org.itliu.algorithm.greed;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @desc 输入: 正数数组costs、正数数组profits、正数K、正数M
 *       costs[i]表示i号项目的花费
 *       profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 *       K表示你只能串行的最多做k个项目
 *       M表示你初始的资金
 *
 *       说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。 输出：你最后获得的最大钱数。
 * @auther itliu
 * @date 2020/5/14
 */
public class Code05_IPO {

    private static int findMaximizedCapital(int K, int M, int[] costs, int[] profits) {
        PriorityQueue<Program> minQueue = new PriorityQueue<>(new MinComparator());
        PriorityQueue<Program> maxQueue = new PriorityQueue<>(new MaxComparator());
        for (int i = 0; i < profits.length; i++) {
            minQueue.add(new Program(costs[i], profits[i]));
        }

        for (int i = 0; i < K; i++) {
            //最小堆不为空&&启动资金<=M(启动资金可以做这个项目)
            while (!minQueue.isEmpty() && (minQueue.peek().c <= M)) {
                maxQueue.add(minQueue.poll());
            }

            if (maxQueue.isEmpty()) {
                return M;
            }

            M += maxQueue.poll().p;
        }
        return M;
    }

    //项目
    private static class Program{
        public int c;//花费
        public int p;//利润

        public Program(int c, int p) {
            this.c = c;
            this.p = p;
        }
    }

    private static class MinComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }

    private static class MaxComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }
}
