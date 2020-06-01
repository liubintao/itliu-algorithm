package org.itliu.algorithm.manacher;

/**
 * @desc 把一个字符串补成回文字符串，要补多少个字符
 * @auther itliu
 * @date 2020/6/1
 */
public class AddShortestEnd {

    private static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int R = -1;
        int C = -1;
        int maxContainsEnd = -1;

        for (int i = 0; i < str.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i] ++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            if (R == str.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }
        
        //要补多少个字符?
        char[] res = new char[s.length() - maxContainsEnd + 1];
        for (int i = 0; i < res.length; i++) {
            //回文串要逆序
            res[res.length - 1 - i] = str[2 * i + 1];
        }
        return String.valueOf(res);
    }

    private static char[] manacherString(String s) {
        final char[] charArray = s.toCharArray();
        char[] res = new char[charArray.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArray[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
