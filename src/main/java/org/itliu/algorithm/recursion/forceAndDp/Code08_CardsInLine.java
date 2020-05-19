package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 范围上尝试的模型
 * <p>
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数。
 * @auther itliu
 * @date 2020/5/18
 */
public class Code08_CardsInLine {

    //暴力递归
    public static int win1(int[] arr) {
        //base case
        if (arr == null || arr.length == 0) {
            return 0;
        }

        return Math.max(f(arr, 0, arr.length - 1),
                s(arr, 0, arr.length - 1));
    }

    // L....R
    // F  S  L+1..R
    // L..R-1
    public static int f(int[] arr, int L, int R) {
        //base case
        if (L == R) {
            return arr[L];
        }
        return Math.max(arr[L] + s(arr, L + 1, R),
                arr[R] + s(arr, L, R - 1));
    }

    public static int s(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        return Math.min(arr[L] + f(arr, L + 1, R),
                arr[R] + f(arr, L, R - 1));
    }

    //暴力递归 -> 动态规划
    public static int win2(int[] arr) {
        //base case
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //可变参数是L，R，取值范围都是0-N-1
        // 原流程依赖f(arr, 0, arr.length - 1)和s(arr, 0, arr.length - 1))，所以设置两个二维数组
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] s = new int[N][N];

        //根据L==R的条件设置初始值
        for (int i = 0; i < N; i++) {
            f[i][i] = arr[i];
        }

//        s[i][i] = 0;
        //填所有的对角线，i=0时的对角线在上面已经初始化完成了，所有从1开始
        for (int i = 1; i < N; i++) {
            int L = 0; //row
            int R = i; //col  并且利用row=0,col=i设置初始值，row永远<col

            //接着干嘛？往右下方填值，将dp[][]每个位置填上值，保证不越界
            while (L < N && R < N) {
                f[L][R] = Math.max(arr[L] + s[L + 1][R],
                        arr[R] + s[L][R - 1]);
                s[L][R] = Math.min(arr[L] + f[L + 1][R],
                        arr[R] + f[L][R - 1]);

                L++;
                R++;
            }
        }

        return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {4, 7, 9, 5, 19, 29, 80, 4};
        // A 4 9
        // B 7 5
        System.out.println(win1(arr));
        System.out.println(win2(arr));

    }
}
