package org.itliu.algorithm.base.sort;

import java.util.Arrays;

/**
 * @desc: 选择排序，从左向右，选择一个最小值与遍历的最左侧元素交换
 * @author: itliu
 * @date: 2020/4/18 22:31
 * @version: 1.0
 */
public class Sort_Selection {

    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        /**
         * 第一层循环：
         * 1、遍历 0~n-1 选择最小元素与索引0的元素交换
         * 2、遍历 1~n-1 选择最小元素与索引1的元素交换
         * 3、遍历 2~n-1 选择最小元素与索引2的元素交换
         * ...
         */
        for (int i = 0; i < arr.length; i++) {//i~n-1
            //最小值的索引
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) { // i ~ N-1 上找最小值的下标
                minIdx = (arr[j] < arr[minIdx]) ? j : minIdx;
            }
            //最小值与最左元素交换
            swap(arr, i, minIdx);
        }
    }

    private static void swap(int[] arr, int i, int minIdx) {
        int tmp = arr[i];
        arr[i] = arr[minIdx];
        arr[minIdx] = tmp;
    }

    //for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    //for test
    //要生成的数组最大大小及最大值
    public static int[] generateRandomArray(int maxSize, int maxVal) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxVal + 1) * Math.random() - maxVal * Math.random());
        }
        return arr;
    }

    //for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }

        return ret;
    }

    //for test
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

    //for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 50_0000;
        int maxValue = 100;
        int maxSize = 100;
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);

            comparator(arr1);
            selectionSort(arr2);

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
        selectionSort(arr);
        printArray(arr);
    }
}
