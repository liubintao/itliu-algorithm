package org.itliu.algorithm.greed;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @desc 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 * 给你每一个项目开始的时间和结束的时间 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。 返回最多的宣讲场次。
 * @auther itliu
 * @date 2020/5/13
 */
public class Code04_BestArrange {

    private static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    // 还剩什么会议都放在programs里
    // done 之前已经安排了多少会议，数量
    // timeLine目前来到的时间点是什么

    // 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    private static int process(Program[] programs, int done, int timeLine) {
        //已经没有会议可以选择了，返回已经安排的会议数量
        if (programs.length == 0) {
            return done;
        }

        //还有会议可以选择
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            //当前会议开始时间可以安排
            if (programs[i].start >= timeLine) {
                //将当前会议从数组中剔除，遍历剩下的可能性
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    //贪心算法求解
    public static int bestArrange2(Program[] programs) {
        if (programs == null && programs.length == 0) {
            return 0;
        }

        Arrays.sort(programs, new MyComparator());

        int timeLine = 0;
        int result = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    private static class MyComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    //对数器
    private static Program[] generatorPrograms(int programSize, int timeMax) {
        Program[] programs = new Program[(int)(Math.random() * (programSize + 1))];
        for (int i = 0; i < programs.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                programs[i] = new Program(r1, r1 + 1);
            } else {
                programs[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return programs;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatorPrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
