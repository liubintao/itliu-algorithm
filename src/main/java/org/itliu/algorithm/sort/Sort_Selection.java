package org.itliu.algorithm.sort;

/**
 * @desc: 选择排序
 * @author: itliu
 * @date: 2020/1/7 9:32
 * @version: 1.0
 */
public class Sort_Selection {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 7, 2, 1, 9, 8, 6, 4};
        sort(arr);
        print(arr);
    }

    private static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i; j < arr.length - 1; j++) {
                if (arr[minIdx] > arr[j + 1]) {
                    minIdx = j + 1;
                }
            }

            if (minIdx > i) {
                arr[minIdx] ^= arr[i];
                arr[i] ^= arr[minIdx];
                arr[minIdx] ^= arr[i];
            }
            print(arr);
            System.out.println();
        }


    }

    private static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
