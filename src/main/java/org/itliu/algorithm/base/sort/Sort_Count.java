package org.itliu.algorithm.base.sort;

/**
 * @desc: 计数排序
 * @author: itliu
 * @date: 2020/4/26 23:33
 * @version: 1.0
 */
public class Sort_Count {

    // only for 0~200 value
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int max = Integer.MIN_VALUE;
        //求数组中最大值，用于申请桶
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        /**
         * 假设桶的下标5的值为3，说明原数组中有3个5，后面排序时，直接根据下标，写入(下标对应的值)个下标
         */
        int[] bucket = new int[max];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }

        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }
}
