package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 从左往右的尝试模型练习
 *       规定1和A对应、2和B对应、3和C对应... 那么一个数字字符串比如"111”就可以转化为: "AAA"、"KA"和"AK"
 *       给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 * @auther itliu
 * @date 2020/5/18
 */
public class Code06_ConvertToLetterString {

    public static int number(String str) {
        //base case
        if (str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    // str[0...i-1]已经转化完了，固定了
    // i之前的位置，如何转化已经做过决定了, 不用再关心
    // i... 有多少种转化的结果
    private static int process(char[] str, int i) {
        //base case
        if (i == str.length) {
            return 1;
        }

        if (str[i] == '0') {
            return 0;
        }

        if (str[i] == '1') {
            int res = process(str, i + 1);
            if (i + 1 < str.length) {
                res += process(str, i + 2);
            }
            return res;
        }

        if (str[i] == '2') {
            int res = process(str, i + 1);
            if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '6') {
                res += process(str, i + 2);// (i和i+1)作为单独的部分，后续有多少种方法
            }
            return res;
        }
        return process(str, i + 1);
    }

    public static int dpWays(String s) {
        //base case
        if (s == null || s.length() == 0) {
            return 0;
        }
        //暴力递归的主函数要谁，要0位置的数，返回dp[0]
        char[] str = s.toCharArray();
        int N = str.length;
        //一共一个可变参数，设置一个一维数组，取值范围设置为N+1，因为有str.length的判断
        int[] dp = new int[N+1];

        //dp怎么生成
        //1、设置初始值
        //根据原过程中if (i == str.length) { return 1; } 将N位置设置为1
        dp[N] = 1;
        //2、将原过程函数copy过来改写
        //原过程中返回的值依赖process(str, i + 1)和process(str, i + 2);所以推断返回值依赖后面的值，先设置后面的值，从后往前遍历
        for (int i = N - 1; i >= 0; i--) {
            //真正的改写过程，所有return的位置改成设置dp
            if (str[i] == '0') {
                dp[i] = 0;
            }

            if (str[i] == '1') {
                dp[i] = dp[i + 1];
                if (i + 1 < str.length) {
                    dp[i] += dp[i + 2];
                }
            }

            if (str[i] == '2') {
                dp[i] = dp[i + 1];
                if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '6') {
                    dp[i] += dp[i + 2];// (i和i+1)作为单独的部分，后续有多少种方法
                }
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(number("111111"));
        System.out.println(dpWays("111111"));
    }

}
