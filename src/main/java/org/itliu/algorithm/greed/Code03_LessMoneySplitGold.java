package org.itliu.algorithm.greed;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @desc 一块金条切成两半，是需要花费和长度数值一样的铜板的。 比如长度为20的金条，不管怎么切，都要花费20个铜板。
 * 一群人想整分整块金条，怎么分最省铜板?   例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，
 * 金条要分成10，20，30三个部分。  如果先把长度60的金条分成10和50，花费60; 再把长度50的金条分成20和30，花费50;
 * 一共花费110铜板。 但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;
 * 一共花费90铜板。 输入一个数组，返回分割的最小代价。
 * @auther itliu
 * @date 2020/5/14
 */
public class Code03_LessMoneySplitGold {

    //暴力求解
    private static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        return process(arr, 0);
    }

    private static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }

        int ans = Integer.MAX_VALUE;
        //两两组合求最小
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansIndex = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansIndex++] = arr[arri];
            }
        }
        ans[ansIndex] = arr[i] + arr[j];
        return ans;
    }

    //贪心算法求解
    private static int lessMoney2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //最小堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            queue.add(arr[i]);
        }

        int sum = 0;
        int cur = 0;
        while (queue.size() > 1) {
            //弹出两个最小的数相加
            cur = queue.poll() + queue.poll();
            sum += cur;
            //将最小两个数的和放入最小堆
            queue.add(cur);
        }
        return sum;
    }

    //对数器
    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] ans = new int[(int) (Math.random() * maxSize + 1)];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * maxValue + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxSize = 6;
        int maxValue = 1000;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int less1 = lessMoney1(arr);
            int less2 = lessMoney2(arr);
            if (less1 != less2) {
                System.out.println(Arrays.toString(arr) + "  " + less1 + " " + less2);
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
