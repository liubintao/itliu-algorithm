package org.itliu.algorithm.base.sort;

import java.util.Arrays;

/**
 * @desc: 基数排序
 * @author: itliu
 * @date: 2020/4/27 18:16
 * @version: 1.0
 */
public class Sort_Radix {

    // only for non-negative value
    public static void radixSort(int[] arr) {
        //base case
        if (arr == null || arr.length < 2) {
            return;
        }

        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    // arr[l..r]排序  ,  digit-最大数字有几位就循环几次
    private static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;//基数为10

        int i = 0, j = 0;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[R - L + 1];
        for (int d = 1; d <= digit; d++) {//有多少位就进出几次
            // 10个空间
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            int[] count = new int[radix];// count[0..9] 范围是基数的范围
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }

            //count' 当前索引对应的是数组中当前位上<=当前索引值的数有几个
            for (i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }

            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[--count[j]] = arr[i];
            }

            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 获取一个数第几位上的数是多少
     *
     * @param x 原数值
     * @param d 第几位
     * @return
     */
    public static int getDigit(int x, int d) {
        return ((x / (int) Math.pow(10, (d - 1))) % 10);
    }

    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }


    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }
}
