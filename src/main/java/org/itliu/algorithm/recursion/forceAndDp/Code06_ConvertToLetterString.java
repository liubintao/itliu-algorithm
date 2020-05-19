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


    public static void main(String[] args) {
        System.out.println(number("11111"));
//        System.out.println(dpWays2("11111"));
    }

}
