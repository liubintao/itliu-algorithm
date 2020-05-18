package org.itliu.algorithm.recursion.forceAndDp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @desc 打印一个字符串的全部子序列
 * @auther itliu
 * @date 2020/5/18
 */
public class Code02_PrintAllSubsquences {

    //主函数
    public static List<String> subs(String str) {
        final char[] charArray = str.toCharArray();
        List<String> ans = new ArrayList<>();
        String path = "";
        process1(charArray, 0, ans, path);
        return ans;
    }

    //暴力递归
    //str固定 位置不变
    //index此时来到的位置，只有两个选择 要 or 不要
    // 如果index来到了str中的终止位置，把沿途路径所形成的答案，放入ans中
    // 之前做出的选择，就是path
    private static void process1(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        //不要当前点，去处理下一个点
        String no = path;
        process1(str, index + 1, ans, no);

        //要当前点，去处理下一个点
        String yes = path + String.valueOf(str[index]);
        process1(str, index + 1, ans, yes);
    }

    public static List<String> subsNoRepeat(String str) {
        final char[] charArray = str.toCharArray();
        Set<String> set = new HashSet<>();
        String path = "";
        process2(charArray, 0, set, path);
        List<String> ans = new ArrayList<>();
        ans.addAll(set);
        return ans;
    }

    private static void process2(char[] str, int index, Set<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        //不要当前点，去处理下一个点
        String no = path;
        process2(str, index + 1, set, no);

        //要当前点，去处理下一个点
        String yes = path + String.valueOf(str[index]);
        process2(str, index + 1, set, yes);
    }


    public static void main(String[] args) {
        String test = "aacc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");
    }
}
