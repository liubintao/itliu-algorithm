package org.itliu.algorithm.recursion.forceAndDp;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 打印一个字符串的全部排列
 * @auther itliu
 * @date 2020/5/18
 */
public class Code03_PrintAllPermutations {

    public static List<String> permutation(String str) {
        //base case
        if (str == null || str.length() == 0) {
            return null;
        }

        final char[] charArray = str.toCharArray();
        List<String> ans = new ArrayList<>();
        process(charArray, 0, ans);
        return ans;
    }

    //str[0..index-1]已经做好决定的
    //str[index...]都有机会来到index位置
    // index终止位置，str当前的样子，就是一种结果 -> ans
    private static void process(char[] str, int index, List<String> ans) {
        //这条路已经走到头了，就把结果收集起来
        if (index == str.length) {
            ans.add(String.valueOf(str));
        }

        for (int j = index; j < str.length; j++) {
            swap(str, index, j);
            process(str, index + 1, ans);
            swap(str, index, j);
        }
    }

    private static void swap(char[] str, int i, int j) {
        char tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }

    //打印一个字符串的全部排列，要求不要出现重复的排列
    public static List<String> permutationNoRepeat(String str) {
        //base case
        if (str == null || str.length() == 0) {
            return null;
        }

        final char[] charArray = str.toCharArray();
        List<String> ans = new ArrayList<>();
        process2(charArray, 0, ans);
        return ans;
    }

    //str[0..index-1]已经做好决定的
    //str[index...]都有机会来到index位置
    // index终止位置，str当前的样子，就是一种结果 -> ans
    private static void process2(char[] str, int index, List<String> ans) {
        //这条路已经走到头了，就把结果收集起来
        if (index == str.length) {
            ans.add(String.valueOf(str));
        }

        boolean[] visit = new boolean[26];// visit[0 1 .. 25]

        for (int j = index; j < str.length; j++) {
            //当前处理的字符没有来到过0位置才进行处理，如果已经来到过0位置，再去0位置就是重复处理了
            // str[j] = 'a'   -> 0   visit[0] -> 'a'
            // str[j] = 'z'   -> 25   visit[25] -> 'z'
            if (!visit[str[j] - 'a']) {
                visit[str[j] - 'a'] = true;
                swap(str, index, j);
                process2(str, index + 1, ans);
                swap(str, index, j);
            }
        }
    }

    public static void main(String[] args) {
        String s = "aac";
        List<String> ans1 = permutation(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutationNoRepeat(s);
        for (String str : ans2) {
            System.out.println(str);
        }

    }
}
