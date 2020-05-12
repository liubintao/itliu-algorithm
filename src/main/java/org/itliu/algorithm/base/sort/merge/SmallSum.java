package org.itliu.algorithm.base.sort.merge;

/**
 * @desc: 归并排序应用-在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 * <p>
 * 什么场景可以使用归并排序呢？计算左边有多少个数比你小，左边有多少个数比你大，右边有多少个数比你小，右边有多少个数比你大，
 * 总结下来就是有多少个的题目都可以用归并排序来解决
 * @author: itliu
 * @date: 2020/4/22 0:11
 * @version: 1.0
 */
public class SmallSum {
    //递归方法实现
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // arr[L...R]范围上，变成有序的
    // L...R    N    T(N) = 2*T(N/2) + O(N)  ->
    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return process(arr, L, mid)
                + process(arr, mid + 1, R)
                + merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;//help数组的索引
        int p1 = L;
        int p2 = mid + 1;
        int res = 0;

        while (p1 <= mid && p2 <= R) {
            //利用右组中的数已经是有序的这个特性，一次性找出当前右组有多少个比当前数大的数
            res += arr[p1] < arr[p2] ? (R - p2 + 1) * arr[p1] : 0;
            //1、左组当前数<右组当前数，copy左组数，计算小和
            //2、左组当前数==右组当前数，copy右组当前数，不计算小和
            //3、左组当前数>右组当前数，copy右组当前数，不计算小和
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        //如果p1/p2越界了，那么就将左组/右组剩余的元素copy进help数组
        //左组/右组只可能有一个有剩余元素，所以下面两个while循环只会有一个执行
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        //最后将help数组中排好序的元素copy给arr
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }

        return res;
    }

    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
