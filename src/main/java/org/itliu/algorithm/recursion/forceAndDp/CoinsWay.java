package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 给定数组arr，arr中所有的值都为正数且不重复 每个值代表一种面值的货币，
 * 每种面值的货币可以使用任意张 再给定一个整数 aim，代表要找的钱数 求组成 aim 的方法数
 * @auther itliu
 * @date 2020/5/19
 */
public class CoinsWay {

    //暴力递归
    // arr中都是正数且无重复值，返回组成aim的方法数
    public static int ways1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        return process1(arr, 0, aim);
    }

    private static int process1(int[] arr, int index, int rest) {
        //base case
        if (rest < 0) {
            return 0;
        }

        //没有钱可分配了，如果正好组成aim，则是一种方法
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }

        int ways = 0;
        //还有剩余的money，应该怎么写条件呢？每个面值的用几张作为条件，zhang * 当前尝试的面值arr[index]不超出总值
        //因为此处设置了zhang * arr[index] <= rest，所以rest不可能<0，可以将第一个base case去掉
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process1(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    //记忆化搜索-自顶向下的动态规划
    public static int ways2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        //设置默认值
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }

        return process2(arr, 0, aim, dp);
    }

    // 如果index和rest的参数组合，是没算过的，dp[index][rest] == -1
    // 如果index和rest的参数组合，是算过的，dp[index][rest]  > - 1
    private static int process2(int[] arr, int index, int rest, int[][] dp) {
        //计算过直接返回
        if (dp[index][rest] != -1) {
            return dp[index][rest];
        }

        //没有计算过先放缓存
        if (index == arr.length) {
            dp[index][rest] = rest == 0 ? 1 : 0;
            return dp[index][rest];
        }

        int ways = 0;
        //还有剩余的money，应该怎么写条件呢？每个面值的用几张作为条件，zhang * 当前尝试的面值arr[index]不超出总值
        //因为此处设置了zhang * arr[index] <= rest，所以rest不可能<0，可以将第一个base case去掉
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process1(arr, index + 1, rest - (zhang * arr[index]));
        }
        dp[index][rest] = ways;
        return ways;
    }

    //经典动态规划，含有枚举行为
    public static int ways3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;//dp[N][1..aim]=0

        //最下面一行的值已经设置了
        //而每一行又依赖index+1行的值，所以行是从N-1到0
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    ways += process1(arr, index + 1, rest - (zhang * arr[index]));
                }
                dp[index][rest] = ways;
            }
        }

        return dp[0][aim];
    }

    //经典动态规划，不含有枚举行为
    public static int ways4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;//dp[N][1..aim]=0

        //最下面一行的值已经设置了
        //而每一行又依赖index+1行的值，所以行是从N-1到0
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }

        return dp[0][aim];
    }

    public static void main(String[] args) {
        int[] arr = {5, 10, 50, 100};
        int sum = 1000;
        System.out.println(ways1(arr, sum));
        System.out.println(ways2(arr, sum));
        System.out.println(ways3(arr, sum));
        System.out.println(ways4(arr, sum));
    }
}
