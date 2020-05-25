package org.itliu.algorithm.stack.monotonousstack;

import java.util.Stack;

/**
 * @desc 给定一个只包含正数的数组arr，arr中任何一个子数组sub， 一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 *       那么所有子数组中，这个值最大是多少？
 * @auther itliu
 * @date 2020/5/25
 */
public class AllTimesMinToMax {

    //暴力解
    private static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                //生成每个子数组的累加和
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, sum * minNum);
            }
        }
        return max;
    }

    //枚举每一个数做最小值的情况，答案必在其中
    private static int max2(int[] arr) {
        //1.求累加和
        int size = arr.length;
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }

        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        //2.枚举数组中每个数作为最小值的子数组
        for (int i = 0; i < size; i++) {
            //栈不空 && 栈顶索引对应的值>=当前索引对应的值   应该出栈
            while (!stack.isEmpty() && (arr[stack.peek()] >= arr[i])) {
                final Integer popIndex = stack.pop();
                //栈顶出栈后：
                /**
                 * 1.栈空，说明左边没有比我小的数了，右边比我小的数是i，以当前数作为最小值的最大范围的累加和就是sum[i-1]
                 *   (sub累加和 )* (sub中的最小值) = sum[i-1](累加和) * arr[popIndex](当前数也就是最小值)
                 * 2.栈不空，为什么是sums[i-1]-sums[stack.peek()] * arr[popIndex]
                 *   右边离你最近的比你小的数在哪是你扩不到的位置也就是i
                 *   左边离你最近的比你小的数在哪是你扩不到的位置也就是当前栈顶的元素stack.peek()
                 *   左右两个位置拿到了，再拿到累加和，则每个数对应的最大范围的指标就可以获得了
                 *   i是右边比我小的数，当前栈的栈顶下面压着的是左边比我小的数，而根据优化技巧arr[L..R]的累加和就等于 0..R的累加和sum[R] - 0..L-1的累加和sum[L-1]
                 *   0..R的累加和就是sums[i-1]，0..L-1的累加和就是sums[stack.peek()，两者相减就是当前值作为最小值的最大范围的累加和
                 *   sums[i-1] - sums[stack.peek()]) * arr[popIndex] = (sub累加和 )* (sub中的最小值)
                 */
                max = Math.max(max, (stack.isEmpty() ? sums[i-1] : sums[i-1] - sums[stack.peek()]) * arr[popIndex]);
            }
            stack.push(i);
        }

        //此时栈里剩余的元素肯定是数组中最后几个由小到大的元素，所以0..R的累加和就是sum[size-1]，
        // 而弹出顺序是大->小，所以每次弹出元素对应的最大范围不变，R还是size-1,
        while (!stack.isEmpty()) {
            final Integer popIndex = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : sums[size - 1] - sums[stack.peek()]) * arr[popIndex]);
        }
        return max;
    }

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 1;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
//            int[] arr = gerenareRondomArray();
            int[] arr = new int[]{3,6,9,3,2,4,6};
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
