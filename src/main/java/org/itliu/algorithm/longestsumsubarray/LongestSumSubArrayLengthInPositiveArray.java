package org.itliu.algorithm.longestsumsubarray;

/**
 * @desc
 *
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 *
 * @auther itliu
 * @date 2020/6/10
 */
public class LongestSumSubArrayLengthInPositiveArray {
    private static int maxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }

        int L = 0;
        int R = 0;
        int sum = arr[0];
        int len = 0;

        //不越界
        while (R < arr.length) {
            if (sum == K) {
                len = Math.max(len, R - L + 1);
                sum -= arr[L++];
            } else if (sum < K) {
                R++;
                if (R == arr.length) {
                    break;
                }
                sum += arr[R];
            } else { //sum > K
                sum -= arr[L++];
            }
        }

        return len;
    }
}
