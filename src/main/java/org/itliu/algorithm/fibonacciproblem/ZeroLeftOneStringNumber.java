package org.itliu.algorithm.fibonacciproblem;

/**
 * @desc 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串  如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 *       返回有多少达标的字符串
 * @auther itliu
 * @date 2020/5/26
 */
public class ZeroLeftOneStringNumber {

    private static int num1(int n) {
        //base case
        if (n < 1) {
            return 0;
        }

        //最左侧放1，i从1位置开始
        return process(1, n);
    }

    //有i的长度，假想在最左侧放一个1
    /**
     * 1.除去最左侧的1，如果在0位置上放0，那么1位置上只能放1，从左往右的模型，只能在i-2上继续放
     * 2.除去最左侧的1，如果在0位置上放1，从左往右的模型，只能在i-1上继续放
     * @param i 剩余待处理的长度
     * @param n
     * @return
     */
    public static int process(int i, int n) {
        //base case
        // i来到了n-1位置，不考虑前面的结果，就剩一个位置了，可以放0和1两种放法
        if (i == n - 1) {
            return 2;
        }

        //i来到了n位置，说明前面的尝试是有效的，返回1
        if (i == n) {
            return 1;
        }

        //i往后扩一个位置+i往后扩两个位置
        return process(i + 1, n) + process(i + 2, n);
    }

    private static int num2(int n) {
        //base case
        if (n < 1) {
            return 0;
        }

        if (n == 1) {
            return 1;//只能放1
        }

        //赋初始值，2个位置只有10和11两种 pre + cur = 2
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i <=n ; i++) {
            tmp = pre + cur;
            pre = cur;
            cur = tmp;
        }

        return cur;
    }

    private static int num3(int n) {
        //base case
        if (n < 1) {
            return 0;
        }


        if (n == 1 || n == 2) {
            return n;
        }

        int[][] base = {{1,1},{1,0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i != 20; i++) {
            System.out.println(num1(i));
            System.out.println(num2(i));
            System.out.println(num3(i));
            System.out.println("===================");
        }

    }
}
