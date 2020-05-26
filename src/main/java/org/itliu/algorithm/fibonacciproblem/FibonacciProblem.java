package org.itliu.algorithm.fibonacciproblem;

/**
 * @desc 斐波那契数列矩阵乘法方式的实现
 * @auther itliu
 * @date 2020/5/25
 */
public class FibonacciProblem {

    //斐波那契数列暴力递归
    private static int f1(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    //斐波那契数列线性求解
    private static int f2(int n) {
        if (n < 1) {
            return 0;
        }

        int f1 = 1;
        int f2 = 1;
        int f3 = 0;
        for (int i = 3; i <= n; i++) {
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }

    //斐波那契数列快速幂求解
    private static int f3(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = {{1, 1}, {1, 0}};
        //假设最后结果为{
        //              {x,y},
        //              {z,k}
        //              }
        //而|F(N),F(N-1)|=|1,1| * res，则N = 1 * x + 1 * z = x+z
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        //搞成对角线上全是1的矩阵
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 经过上一步之后，res = 矩阵中的1
        int[][] tmp = m;// 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, tmp);
            }
            //t每次自己和自己玩，得到下一步的t
            tmp = multiMatrix(tmp, tmp);
        }
        return res;
    }
    //两个矩阵乘完之后的结果
    public static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {//矩阵1有几行
            for (int j = 0; j < m2[0].length; j++) {//矩阵2有几列
                for (int k = 0; k < m2.length; k++) {//矩阵2有几行
                    //矩阵1中每一行中每一列的值 * 矩阵2中每一行每一列的值
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    /**
     * 一个人可以一次往上迈1个台阶，也可以迈2个台阶
     * 返回这个人迈上N级台阶的方法数
     *
     * @param n N级台阶
     * @return 方法数
     */
    public static int s1(int n) {
        if (n < 1) {
            return 0;
        }

        /**
         * 迈1阶台阶只有1种方法，迈2阶台阶有2种方法(1次迈1阶迈2次  1次迈2阶迈一次)
         */
        if (n == 1 || n == 2) {
            return n;
        }
        /**
         * 只能从第n-1阶台阶迈上来或者从n-2阶台阶迈上来
         */
        return s1(n - 1) + s1(n - 2);
    }

    /**
     * 迈台阶的线性复杂度求解 O(N)
     */
    public static int s2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    //迈台阶的斐波那契数列快速幂求解
    public static int s3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    /**
     * 第一年农场有1只成熟的母牛A，往后的每年：
     *
     * 1）每一只成熟的母牛都会生一只母牛
     * 2）每一只新出生的母牛都在出生的第三年成熟
     * 3）每一只母牛永远不会死
     * 返回N年后牛的数量
     *
     * @param n n年后
     * @return 牛的数量
     */
    //暴力递归
    public static int c1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    //线性求解
    public static int c2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int prepre = 1;//三年前1只
        int pre = 2;//两年前又生了1只共2只
        int res = 3;//前一年又生了一只共3只
        int tmp2 = 0;
        //从第4年开始  每年数量=前一年数量+三年前数量
        for (int i = 4; i <= n; i++) {
            tmp2 = pre;
            pre = res; //三年前数量变成两年前数量
            res = res + prepre; //返回的结果=前一年数量+三年前数量
            prepre = tmp2; //三年前数量
        }
        return res;
    }

    //快速幂方式求解
    public static int c3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = { { 1, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    public static void main(String[] args) {
        int n = 20;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("===");

        System.out.println(s1(n));
        System.out.println(s2(n));
        System.out.println(s3(n));
        System.out.println("===");

        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
        System.out.println("===");

    }
}
