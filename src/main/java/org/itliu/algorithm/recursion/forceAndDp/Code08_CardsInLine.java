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

    public static void main(String[] args) {
        int[] arr = { 4,7,9,5,19,29,80,4 };
        // A 4 9
        // B 7 5
        System.out.println(win1(arr));
//        System.out.println(win2(arr));

    }
}
