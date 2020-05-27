package org.itliu.algorithm.bfprt;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @desc 在无序数组中求第K小的数
 *       1）改写快排的方法
 *       2）bfprt算法
 *
 *       只要是无序数组，求第K....的问题，都用bfprt算法解
 *
 * @auther itliu
 * @date 2020/5/27
 */
public class FindMinKth {
    private static class MaxComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    /**
     * 利用大根堆特性：根节点的数最大， 第K小就是求K个数中最大的那个，只有小于堆顶的才进入堆
     * 时间复杂度 maxheap的时间复杂度是logK,N个数都会进行是否入堆得判断，最差情况N个数都会入堆，总的时间复杂度是O(N*logK)
     * @param arr 数组
     * @param k 指标
     * @return 指标对应的值
     */
    private static int minKth1(int[] arr, int k) {
        Queue<Integer> maxHeap = new PriorityQueue<>(new MaxComparator());
        //将前k个数放进大根堆
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }

        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    /**
     * 利用荷兰国旗快排
     */
    private static int minKth2(int[] arr, int k) {
        int[] copyArr = copyArray(arr);
        return process2(copyArr, 0, arr.length - 1, k - 1);
    }

    private static int process2(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }

        //随机选取一个数
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]){
            return process2(arr, L, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, R, index);
        }
    }

    private static int minKth3(int[] arr, int k) {
        int[] copyArr = copyArray(arr);
        return bfprt(copyArr, 0, arr.length - 1, k - 1);
    }

    private static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }

        //取中位数的中位数
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]){
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    private static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        //每组5个，组内排序，组中位数数组
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    private static int getMedian(int[] arr, int L, int R) {
        //搞一个插入排序
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    private static void insertionSort(int[] arr, int L, int R) {
        /**
         * 0-0已经有序，所以从1开始，每次一步一步往前看
         */
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        //保证不越界
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] == pivot) {
                cur++;
            } else {
                swap(arr, cur, --more);
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
