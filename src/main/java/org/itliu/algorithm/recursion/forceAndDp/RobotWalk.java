package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 规定机器人必须走 K 步，最终能来到P位置
 * <p>
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数。
 * @auther itliu
 * @date 2020/5/19
 */
public class RobotWalk {

    //暴力递归解法
    public static int ways1(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }

        // 总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return walk(N, M, K, P);
    }

    // N : 位置为1 ~ N，固定参数
    // cur : 当前在cur位置，可变参数
    // rest : 还剩res步没有走，可变参数
    // P : 最终目标位置是P，固定参数
    // 该函数的含义：只能在1~N这些位置上移动，当前在cur位置，走完rest步之后，停在P位置的方法数作为返回值返回
    private static int walk(int N, int cur, int rest, int P) {
        // base case
        // 如果没有剩余步数了，当前的cur位置就是最后的位置
        // 如果最后的位置停在P上，那么之前做的移动是有效的
        // 如果最后的位置没在P上，那么之前做的移动是无效的
        if (rest == 0) {
            return cur == P ? 1 : 0;
        }

        // 如果还有rest步要走，而当前的cur位置在1位置上，那么当前这步只能从1走向2
        // 后续的过程就是，来到2位置上，还剩rest-1步要走
        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }

        // 如果还有rest步要走，而当前的cur位置在N位置上，那么当前这步只能从N走向N-1
        // 后续的过程就是，来到N-1位置上，还剩rest-1步要走
        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }

        // 如果还有rest步要走，而当前的cur位置在中间位置上，那么当前这步可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到cur-1位置上，还剩rest-1步要走
        // 走向右之后，后续的过程就是，来到cur+1位置上，还剩rest-1步要走
        // 走向左、走向右是截然不同的方法，所以总方法数要都算上
        return walk(N, cur - 1, rest - 1, P) + walk(N, cur + 1, rest - 1, P);
    }

    //暴力递归->动态规划演进  可变参数组合->结构化缓存
    public static int waysCache(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        //两个可变参数M K，尝试的范围是N K，当前问题中的N的范围是1-N，我们给定数组范围为N+1，保证能放下所有的解
        int[][] dp = new int[N + 1][K + 1];
        //后面所有递归过程都带着dp玩，问题是怎么给dp赋值
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < K; col++) {
                dp[row][col] = -1;
            }
        }
        return walkCache(N, M, K, P, dp);
    }

    // HashMap<String, Integer>   (19,100)  "19_100"
    // 我想把所有cur和rest的组合，返回的结果，加入到缓存里
    private static int walkCache(int N, int cur, int rest, int P, int[][] dp) {
        if (dp[cur][rest] != -1) {//已经有缓存了
            return dp[cur][rest];
        }

        //其他步骤该怎么走还是怎么走，只是把结果都缓存了
        if (cur == 1) {
            dp[cur][rest] = walkCache(N, 2, rest - 1, P, dp);
            return dp[cur][rest];
        }

        if (cur == N) {
            dp[cur][rest] = walkCache(N, N - 1, rest - 1, P, dp);
            return dp[cur][rest];
        }

        dp[cur][rest] = walkCache(N, cur - 1, rest - 1, P, dp)
                + walkCache(N, cur + 1, rest - 1, P, dp);
        return dp[cur][rest];
    }
}
