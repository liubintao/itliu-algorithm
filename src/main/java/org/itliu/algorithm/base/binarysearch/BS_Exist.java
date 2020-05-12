package org.itliu.algorithm.base.binarysearch;

import java.util.Arrays;

/**
 * @desc: binary search a number exist，在一个有序数组中使用二分搜索查看一个数是否存在
 * @author: itliu
 * @date: 2020/4/20 10:54
 * @version: 1.0
 */
public class BS_Exist {

    /**
     * 在一个有序数组中使用二分搜索查看一个数是否存在
     *
     * @param sortedArr 有序数组
     * @param num       要查询的数值
     * @return 是否存在
     */
    public static boolean exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }

        /**
         * 要搜索的范围数0 ~ arr.length-1
         * 使用二分的话需要定义左右边界
         */
        int L = 0;
        int R = sortedArr.length - 1;
        int mid;
        /**
         * 搜索范围是L..R，终止条件是L < R
         */
        while (L < R) {
            /**
             * L + (R - L) >> 1 == (L + R) / 2
             * 那为什么要这么写呢？为了防止越界
             * 假设L的值很大，那么再加上R就越界了，与我们真正想得到的值就不一样了
             * R存在，肯定没有越界，L<R, R-L也肯定没有越界, (R-L) >> 1更不会越界了
             * >>1 == /2，因为位运算是最快的
             */
            mid = L + ((R - L) >> 1);
            /**
             * 有了mid之后，就可以拿mid与查找的值进行比对了，因为数组是有序的，要查找的值只有三种情况：
             * 1、mid == num
             * 2、mid > num -> 在mid左边查找
             * 3、mid < num -> 在mid右边查找
             */
            if (sortedArr[mid] == num) {
                return true;
            } else if (sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        /**
         * 跳出while循环后，再判断一次
         */
        return sortedArr[L] == num;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() ->  [0,1) 所有的小数，等概率返回一个
        // Math.random() * N -> [0,N) 所有小数，等概率返回一个
        // (int)(Math.random() * N) -> [0,N-1] 所有的整数，等概率返回一个
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())]; // 长度随机
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random())
                    - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100; // 随机数组的长度0～100
        int maxValue = 100;// 值：-100～100
        int num;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
//            System.out.println(i);
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            if (arr1.length == 0) continue;
            Arrays.sort(arr1);
            int idx = (int) (arr1.length * Math.random());
//            System.out.println("数组大小：" + arr1.length + " " + "当前索引：" + idx);
            num = arr1[idx];
            if (!exist(arr1, num)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
