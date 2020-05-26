package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 马走日，在当前位置，走K步，来到x,y位置的方法有多少种
 * @auther itliu
 * @date 2020/5/25
 */
public class KnightWay {
    //x,y为当前坐标，k还剩几步
    public static int ways1(int x, int y, int k) {
        return f(x, y, k);
    }

    //马从(0,0)出发，还有K步要走，并且一定要走完，最终来到x,y位置的方法数是多少
    //也相当于从(x,y)出发达到(0,0)
    private static int f(int x, int y, int k) {
        // base case 1 让你走0步，来到0,0，也就是你不动，只有一种方法
        if (k == 0) {
            return x == 0 && y == 0 ? 1 : 0;
        }

        //有步数要走，有哪些可能性能来到x,y坐标
        // base case 2 越界了
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }

        //有步数要走，x,y也是棋盘上的位置
        return f(x - 1, y + 2, k - 1)
                + f(x - 1, y - 2, k - 1)
                + f(x - 2, y + 1, k - 1)
                + f(x - 2, y - 1, k - 1)
                + f(x + 1, y + 2, k - 1)
                + f(x + 1, y - 2, k - 1)
                + f(x + 2, y + 1, k - 1)
                + f(x + 2, y - 1, k - 1);
    }

    public static int ways2(int x, int y, int k) {
        //三个可变参数，给一个三维表
        //x范围0-9，y范围0-8，k是要走k步，包含k，所以给k+1
        int[][][] dp = new int[10][9][k + 1];
        //这张表是啥？怎么填？先看它怎么依赖的
        //根据base case k=0时x=0,y=0来填写dp[0][0][0]=1，其他k=0的情况dp[..][..][0]都是0
        dp[0][0][0] = 1; //dp[..][..][0] = 0
        //上面将第0层的面填完了，我们只要填完上面的层，按照主函数返回dp[x][y][k]即可
        //根据之前的递归函数可知，第K层依赖K-1层，然而第0层的值我们填完了，直接从第1层开始
        for (int level = 1; level <= k; level++) {//遍历每一层
            for (int i = 0; i < 10; i++) { //X可能性
                for (int j = 0; j < 9; j++) { //Y可能性
                    dp[i][j][level] =
                            getValue(dp, i - 1, j + 2, level - 1)
                                    + getValue(dp, i - 1, j - 2, level - 1)
                                    + getValue(dp, i - 2, j + 1, level - 1)
                                    + getValue(dp, i - 2, j - 1, level - 1)
                                    + getValue(dp, i + 1, j + 2, level - 1)
                                    + getValue(dp, i + 1, j - 2, level - 1)
                                    + getValue(dp, i + 2, j + 1, level - 1)
                                    + getValue(dp, i + 2, j - 1, level - 1);
                }
            }
        }

        return dp[x][y][k];
    }

    private static int getValue(int[][][] dp, int x, int y, int k) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][k];
    }

    public static void main(String[] args) {
        int x = 2;
        int y = 3;
        int k = 3;
        System.out.println(ways1(x, y, k));
        System.out.println(ways2(x, y, k));
    }
}
