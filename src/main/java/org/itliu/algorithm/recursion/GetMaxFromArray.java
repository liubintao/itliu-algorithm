package org.itliu.algorithm.recursion;

/**
 * @desc: 使用递归找出数组中的最大数，遍历一遍不就找到了吗？我不，我偏不
 * @author: itliu
 * @date: 2020/4/21 16:44
 * @version: 1.0
 */
public class GetMaxFromArray {

    public int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    // arr[L..R]范围上求最大值  L ... R   N
    private int process(int[] arr, int L, int R) {
        if (L == R) {// arr[L..R]范围上只有一个数，直接返回，base case
            return arr[L];
        }

        int mid = L + ((R - L) >> 1);
        int leftMax = process(arr, L, mid);
        int rightMxt = process(arr, mid + 1, R);
        return Math.max(leftMax, rightMxt);
    }
}
