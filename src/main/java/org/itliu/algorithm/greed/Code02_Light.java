package org.itliu.algorithm.greed;

import java.util.HashSet;

/**
 * @desc 给定一个字符串str，只由‘X’和‘.’两种字符构成。 ‘X’表示墙，不能放灯，也不需要点亮 ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 * @auther itliu
 * @date 2020/5/13
 */
public class Code02_Light {

    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }

        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (str.length == index) {// 结束的时候，要从所有结果中将不可能的结果去掉
            for (int i = 0; i < str.length; i++) {
                if (str[i] == '.') {//当前位置是.的时候，i-1  i  i+1这三个位置必有一盏灯
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else {// str还没结束
            //i位置上要么是X要么是.
            //i位置上不放灯的情况，只考虑i+1位置
            int no = process(str, index + 1, lights);
            //i位置上考虑放不放灯，只有i位置上是.的时候才能放灯
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                //回溯到index位置，将index移除，考虑index平行的可能性
                lights.remove(index);
            }
            return Math.min(yes, no);
        }
    }

    //贪心算法求解
    private static int minLight2(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        final char[] strs = road.toCharArray();
        int index = 0;
        int light = 0;
        /**
         * 分析可能性
         * 1.i -> X，只考虑i+1位置
         * 2.i -> .
         *        1.i+1 -> X，考虑i+2位置  要放灯
         *        2.i+1 -> .
         *                   1.i+2 -> X，考虑i+3位置，要放灯
         *                   2.i+2 -> .，考虑i+3位置，要放灯
         *
         * 总结：
         * 1.i位置是X，考虑下个位置
         * 2.i位置是.，肯定要放灯
         *          i+1是X，考虑i+2
         *          i+1是.，考虑i+3
         */
        while (index < strs.length) {
            if (strs[index] == 'X') {
                index++;
            } else {
                light++;
                if (index + 1 == strs.length) {
                    break;
                } else {
                    if (strs[index + 1] == 'X') {
                        index += 2;
                    } else {
                        index += 3;
                    }
                }
            }
        }
        return light;
    }

    //对数器
    private static String generateString(int length) {
        char[] res = new char[(int) (Math.random() * length) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int length = 10;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            String road = generateString(length);
            if (minLight1(road) != minLight2(road)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
