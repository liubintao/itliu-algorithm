package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 两个字符串的最长公共子序列长度问题
 * @auther itliu
 * @date 2020/5/20
 */
public class Code05_PalindromeSubsequence {

    public static int lcse(char[] str1, char[] str2) {

        int[][] dp = new int[str1.length][str2.length];

        //第一行第一列有没有公共子序列最好计算
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        //填充行值
        for (int i = 1; i < str1.length; i++) { //每一行依赖上一行的值
            dp[i][0] = Math.max(dp[i - 1][0], (str1[i] == str2[0] ? 1 : 0));
        }
        //填充列值
        for (int i = 1; i < str2.length; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], (str1[0] == str2[i] ? 1 : 0));
        }

        //共四种可能性
        /**
         * 1.来自str1[i-1][j-1] 左上的值
         * 2.来自str1[i-1][j] 上面的值
         * 3.来自str1[i][j-1] 左面的值
         * 4.str1[i] == str2[j] 的情况下，str1[i-1][j-1] + 1
         *
         * 可能性2决策的时候依赖可能性1，可能性3决策的时候依赖可能性1，可能性2和3都不会比可能性1小，所以决策可能性234即可
         */
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                //可能性2、3找最大
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[str1.length - 1][str2.length - 1];
    }

    public static void main(String[] args) {
        String s1 = "a134b";
        String s2 = "c314y";

        System.out.println(lcse(s1.toCharArray(), s2.toCharArray()));
    }
}
