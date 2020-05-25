package org.itliu.algorithm.stack.monotonousstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @desc 单调栈   一种特别设计的栈结构，为了解决如下的问题：
 * <p>
 * 给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
 * 1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
 * 如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
 * @auther itliu
 * @date 2020/5/25
 */
public class MonotonousStack {

    public static int[][] getNearLessNoRepeat(int[] arr) {
        //返回结构
        int[][] res = new int[arr.length][2];
        //存储中间结果的单调栈,存储索引
        Stack<Integer> stack = new Stack<>();

        //开始遍历，需要做几件事：
        for (int i = 0; i < arr.length; i++) {
            //1.栈从小到大，<栈顶则弹出栈顶，弹出生成记录
            while (!stack.isEmpty() && (arr[stack.peek()] > arr[i])) {
                final Integer popIndex = stack.pop();
                final int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;//谁让它出栈的
            }
            //2.压栈，不大于那就小于
            stack.push(i);
        }

        //栈中剩余记录生成返回结果
        while (!stack.isEmpty()) {
            final Integer popIndex = stack.pop();
            final int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftLessIndex;
            res[popIndex][1] = -1;
        }
        return res;
    }

    /**
     * arr [3,2,1,4,5]
     * 0 1 2 3 4
     * <p>
     * 为什么返回矩阵
     * [
     * 0:[-1,1]
     * 1:[-1,2]
     * ....
     * ]
     *
     * @param arr 数组
     * @return 每个数的左右比它小的位置的矩阵
     */
    public static int[][] getNearLess(int[] arr) {
        //返回结果,每个数都有左右比它小的两个数的数组
        int[][] res = new int[arr.length][2];
        // List<Integer> -> 放的是位置，同样值的东西，位置压在一起
        Stack<List<Integer>> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {// i -> arr[i] 进栈
            // 底 -> 顶， 小 -> 大
            while (!stack.isEmpty() && (arr[stack.peek().get(0)] > arr[i])) {
                //弹出生成记录
                final List<Integer> popList = stack.pop();
                // 取位于下面位置的列表中，最晚加入的那个
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popIndex : popList) {
                    res[popIndex][0] = leftLessIndex;
                    res[popIndex][1] = i;
                }
            }

            //当前值>=栈顶元素
            //栈不为空且栈顶集合中元素==arr[i]，就放入栈顶的List
            if (!stack.isEmpty() && (arr[stack.peek().get(0)]) == arr[i]) {
                stack.peek().add(i);
            } else {
                //栈为空就新建List压栈
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }

        //栈中剩余元素生成记录
        while (!stack.isEmpty()) {
            final List<Integer> popList = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popIndex : popList) {
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = -1;
            }
        }
        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
    }
}
