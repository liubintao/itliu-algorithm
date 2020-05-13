package org.itliu.algorithm.greed;

import java.util.*;

/**
 * @desc 给定一个由字符串组成的数组strs， 必须把所有的字符串拼接起来， 返回所有可能的拼接结果中，字典序最小的结果
 * @auther itliu
 * @date 2020/5/13
 */
public class Code01_LowestLexicography {

    //暴力求解-递归
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        List<String> all = new ArrayList<>();
        Set<Integer> use = new HashSet<>();
        process(strs, use, "", all);
        String lowest = all.get(0);
        for (int i = 1; i < all.size(); i++) {
            if (all.get(i).compareTo(lowest) < 0) {
                lowest = all.get(i);
            }
        }
        return lowest;
    }

    // strs里放着所有的字符串
    // 已经使用过的字符串的下标，在use里登记了，不要再使用了
    // 之前使用过的字符串，拼接成了-> path
    // 用all收集所有可能的拼接结果
    private static void process(String[] strs, Set<Integer> use, String path, List<String> all) {
        //终止条件，已经使用过的字符串下标数量==strs.length
        if (use.size() == strs.length) {
            all.add(path);
        } else {
            for (int i = 0; i < strs.length; i++) {
                if (!use.contains(i)) {
                    use.add(i);
                    process(strs, use, path + strs[i], all);
                    //回溯时，开启平行可能性时使用
                    use.remove(i);
                }
            }
        }
    }

    //贪心算法求解
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }

    private static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    //对数器
    public static String generateRandomString(int strLength) {
        char[] ans = new char[(int) (Math.random() * strLength) + 1];
//        char[] random = new char[]{65, 97};
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
//            ans[i] = (char) (random[(int) (Math.random() * 2)] + value);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] generateRandomStringArray(int arrLength, int strLength) {
        String[] ans = new String[(int) Math.random() * arrLength + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLength);
        }
        return ans;
    }

    public static String[] copyStringArray(String[] arr) {
        String[] copyArr = new String[arr.length];
        for (int i = 0; i < copyArr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;
    }

    public static void main(String[] args) {
        int arrLength = 6;
        int strLength = 5;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLength, strLength);
            String[] copyArr = copyStringArray(arr);
            if (!lowestString1(arr).equals(lowestString2(copyArr))) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
