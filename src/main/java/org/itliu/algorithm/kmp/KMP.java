package org.itliu.algorithm.kmp;

/**
 * @desc KMP算法
 * <p>
 * 假设字符串str长度为N，字符串match长度为M，M <= N
 * 想确定str中是否有某个子串是等于match的。
 * 时间复杂度O(N)
 * @auther itliu
 * @date 2020/5/26
 */
public class KMP {
    /**
     * @param s 字符串str
     * @param m 字符串match
     * @return m在s中的位置
     */
    private static int getIndexOf(String s, String m) {
        if (s == null || m == null || s.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        final char[] str = s.toCharArray();
        final char[] match = m.toCharArray();

        //求解一个包含最大前缀和最大后缀相等值得数组
        int[] next = getNextArray(match); //next[i]指标含义：match中i之前的字符串match[0..i-1]的最大匹配长度
        //给两个指针，在str1和str2上移动使用
        int x = 0;//str中当前比对到的位置
        int y = 0;//match中当前比对到的位置
        //如果str没越界，match没越界才循环处理
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) { //字符相等，同时往后走
                x++;
                y++;
            } else if (next[y] == -1) { //y==0，y已经来到0位置了，你换一个开头从头开始匹配
                x++;
            } else {//y还能往前跳，X别动，Y就往前跳
                y = next[y];
            }
        }

        //1) y没有越界 返回-1
        //2) y越界了证明匹配
        return y == match.length ? x - y : -1;
    }

    private static int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[]{-1}; // 没有最大前缀和最大后缀
        }

        int[] next = new int[ms.length];
        //前两个字符的位置手工指定，0位置-1，后面可判断是否需要从头匹配，1位置0，代表1位置没有最大前缀和最大后缀
        next[0] = -1;
        next[1] = 0;

        int i = 2;
        // cn代表
        // 1.i-1位置下，最大匹配的长度
        // 2.cn位置的字符，是当前和i-1位置比较的字符(当前哪个字符在和i-1位置字符比较)
        int cn = 0;
        while (i < next.length) { //如何遍历一遍就找到最大前缀和最大后缀长度
            if (ms[i-1] == ms[cn]) {
//                next[i] = cn + 1;
//                i++;
//                cn++;
                next[i++] = ++cn;
            } else if (cn > 0) {//往前跳一次，重新匹配
                cn = next[cn];
            } else {//cn跳到-1，就是0
                next[i++] = 0;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");


        System.out.println(5/2);
    }
}
