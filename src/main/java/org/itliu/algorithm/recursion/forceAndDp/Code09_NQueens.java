package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc N皇后
 * <p>
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 * @auther itliu
 * @date 2020/5/19
 */
public class Code09_NQueens {

    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        //皇后放哪，用一个一维数组即可表示，下标代表行，下标值代表列
        int[] record = new int[n];
        return process1(0, record, n);
    }

    // 潜台词：record[0..i-1]的皇后，任何两个皇后一定都不共行、不共列，不共斜线
    // 目前来到了第i行
    // record[0..i-1]表示之前的行，放了的皇后位置
    // n代表整体一共有多少行  0~n-1行  n作为终止条件
    // 返回值是，摆完所有的皇后，合理的摆法有多少种
    private static int process1(int i, int[] record, int n) {
        //base case
        //record一共n行也就是0..n-1，当i==n时说明已经走完了所有的行，肯定有一种摆法
        if (i == n) {
            return 1;
        }

        int res = 0;
        // 没有到终止位置，还有皇后要摆，那就要遍历所有的列来尝试
        for (int j = 0; j < n; j++) {// 当前行在i行，尝试i行所有的列  -> j
            // 当前i行的皇后，放在j列，会不会和之前(0..i-1)的皇后，不共行共列或者共斜线，
            // 如果是，认为有效
            // 如果不是，认为无效
            if (isValid(record, i, j)) {
                //将i行j列放上皇后
                record[i] = j;
                //继续尝试下一行
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }

    // record[0..i-1]你需要看，record[i...]不需要看
    // 返回i行皇后，放在了j列，是否有效
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (j == record[k] //共列
                    || (Math.abs(k - i) == Math.abs(record[k] - j))) { //共斜线
                return false;
            }
        }
        return true;
    }


    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // limit 划定了问题的规模 -> 固定

    // colLim 列的限制，1的位置不能放皇后，0的位置可以
    // leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
    // rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
    private static int process2(int limit, int colLimit, int leftDiaLimit, int rightDiaLimit) {
        //base case
        //每一列上都放了皇后
        if (colLimit == limit) {
            return 1;
        }

        // 所有可以放皇后的位置，都在pos上
        // colLim | leftDiaLim | rightDiaLim   -> 总限制，之前的行已经放了皇后的位置
        // ~ (colLim | leftDiaLim | rightDiaLim) -> 左侧的一坨0干扰，右侧每个1，可尝试，也就是说之前的行没有放皇后的位置
        int pos = limit & (~(colLimit | leftDiaLimit | rightDiaLimit));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 其取出pos中，最右侧的1来，剩下位置都是0
            mostRightOne = pos & (~pos + 1);
            //消掉最后一个1
            pos = pos - mostRightOne;
            res += process2(limit,
                    colLimit | mostRightOne, //最右侧1的位置的列上放了皇后
                    (leftDiaLimit | mostRightOne) << 1, //列左移一位就是左斜线的位置
                    (rightDiaLimit | mostRightOne) >>> 1); //列无符号右移一位就是右斜线的位置，且左补零
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println(num1(n));
        System.out.println("==========================");
        System.out.println(num2(n));
    }
}
