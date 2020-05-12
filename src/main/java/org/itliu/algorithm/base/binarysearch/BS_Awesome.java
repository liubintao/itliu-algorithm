package org.itliu.algorithm.base.binarysearch;

/**
 * @desc: 找局部最小，无序，相邻不等
 * @author: itliu
 * @date: 2020/4/20 16:28
 * @version: 1.0
 */
public class BS_Awesome {

    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        /**
         * 1、数组只有一个元素，我认为这个元素就是局部最小
         * 2、数组第一个元素<第二个元素，我就认为第一个元素是局部最小
         */
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }

        /**
         * 如果最后一个元素<它之前的元素，我就认为它是局部最小
         */
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        /**
         * 如果一个元素既<它之前的元素，又<它之后的元素，我就认为它是局部最小
         */
        int L = 1;
        int R = arr.length - 2;
        int mid;
        while (L < R) {
            mid = L + ((R - L) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return L;

    }
}
