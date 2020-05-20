package org.itliu.algorithm.recursion.forceAndDp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @desc 给定一个数组，代表每个人喝完咖啡准备刷杯子的时间 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，
 * 洗完才能洗下一杯 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发 返回让所有咖啡杯变干净的最早完成时间
 * 三个参数：int[] arr、int a、int b
 * @auther itliu
 * @date 2020/5/20
 */
public class Code06_Coffee {

    // 方法一：暴力尝试方法
    public static int minTime1(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 方法一，每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    // 方法一，暴力尝试洗咖啡杯的方式
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }

    // 方法二：稍微好一点的解法
    public static class Machine {
        public int timePoint;
        public int workTime;

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    public static class MachineComparator implements Comparator<Machine> {

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }

    }

    // 方法二，每个人暴力尝试用每一个咖啡机给自己做咖啡，优化成贪心
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return process(drinks, a, b, 0, 0);
    }

    // 方法二，洗咖啡杯的方式和原来一样，只是这个暴力版本减少了一个可变参数

    // process(drinks, 3, 10, 0,0)
    // a 洗一杯的时间 固定变量
    // b 自己挥发干净的时间 固定变量
    // drinks 每一个员工喝完的时间 固定变量
    // drinks[0..index-1]都已经干净了，不用你操心了
    // drinks[index...]都想变干净，这是我操心的，washLine表示洗的机器何时可用
    // drinks[index...]变干净，最少的时间点返回
    public static int process(int[] drinks, int a, int b, int index, int washLine) {
        //base case
        if (index == drinks.length - 1) {
            //最后一杯洗的时间(可以洗的时间点和喝完最后一杯的时间点取最大+洗的时间)和挥发的时间取最小
            return Math.min(Math.max(washLine, drinks[index]) + a, drinks[index] + b);
        }

        // 剩不止一杯咖啡
        // wash是我当前的咖啡杯，洗完的时间
        int wash = Math.max(washLine, drinks[index]) + a; // 洗，index一杯，结束的时间点
        //index..后面的杯子变干净的最早时间
        int next1 = process(drinks, a, b, index + 1, wash);
        int p1 = Math.max(wash, next1);

        //挥发的方式
        int dry = drinks[index] + b;// 挥发，index一杯，结束的时间点
        int next2 = process(drinks, a, b, index + 1, washLine);
        int p2 = Math.max(dry, next2);

        return Math.min(p1, p2);
    }

    //动态规划，但是
    public static int dp(int[] drinks, int a, int b) {
        //洗一杯比挥发一杯用的时候还长，那还洗它干什么
        if (a > b) {
            return drinks[drinks.length - 1] + b;
        }

        int N = drinks.length;
        int limit = 0; // 咖啡机什么时候可用，根据业务规则确定可变参数的范围
        for (int i = 0; i < N; i++) {
            limit += Math.max(limit, drinks[i]) + a;
        }

        int[][] dp = new int[N][limit + 1];
        // N-1行，所有的值
        for (int washLine = 0; washLine <= limit; washLine++) {
            dp[N - 1][washLine] = Math.min(Math.max(washLine, drinks[N-1]) + a, drinks[N-1] + b);
        }

        for (int index = N - 2; index >= 0 ; index--) {
            for (int washLine = 0; washLine <= limit; washLine++) {

                int p1 = Integer.MAX_VALUE;
                int wash = Math.max(washLine, drinks[index]) + a;
                if (wash <= limit) {
                    p1 = Math.max(wash, dp[index + 1][wash]);
                }

                int p2 = Math.max(drinks[index] + b, dp[index + 1][washLine]);

                dp[index][washLine] = Math.min(p1, p2);
            }
        }

        //每次决策都依赖后面一杯及后面时间点的值，所以直接返回dp[0][0]
        return dp[0][0];
    }

    // 方法三：最终版本，把方法二洗咖啡杯的暴力尝试进一步优化成动态规划
    public static int minTime3(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        if (a >= b) {
            return drinks[n - 1] + b;
        }
        int[][] dp = new int[n][drinks[n - 1] + n * a];
        for (int i = 0; i < dp[0].length; i++) {
            dp[n - 1][i] = Math.min(Math.max(i, drinks[n - 1]) + a, drinks[n - 1] + b);
        }
        for (int row = n - 2; row >= 0; row--) { // row 咖啡杯的编号
            int washLine = drinks[row] + (row + 1) * a;
            for (int col = 0; col < washLine; col++) {
                int wash = Math.max(col, drinks[row]) + a;
                dp[row][col] = Math.min(Math.max(wash, dp[row + 1][wash]), Math.max(drinks[row] + b, dp[row + 1][col]));
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[] drinks = {1,1,5,5,7,10,12,12,12,12,12,12,15};
        int a = 3;
        int b = 10;
        System.out.println(process(drinks, 3, 10, 0, 0));
        System.out.println(dp(drinks, a, b));
    }
}
