package org.itliu.algorithm.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @desc 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
 * @auther itliu
 * @date 2020/5/25
 */
public class Lc59_SlidingWindowMaxArray {
    public static int[] getMaxWindow(int[] arr, int w) {
        //base case
        //1.数组为空  2.没有窗口  3.形成不了窗口
        if (arr == null || w < 1 || arr.length < w) {
            return new int[0];
        }

        //双端队列
        Deque<Integer> queue = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;

        for (int R = 0; R < arr.length; R++) {
            //严格从大到小，不符合的弹出
            while (!queue.isEmpty() && (arr[queue.peekLast()] <= arr[R])) {
                queue.pollLast();
            }
            queue.addLast(R);

            //只判断该弹出的
            if (queue.peekFirst() == R - w) {
                queue.pollFirst();
            }

            //形成窗口大小后才进入
            if (R >= w - 1) {
                res[index++] = arr[queue.peekFirst()];
            }
        }
        return res;
    }

    // for test
    public static int[] rightWay(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < arr.length) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = rightWay(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
