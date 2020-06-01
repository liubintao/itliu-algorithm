package org.itliu.algorithm.manacher;

/**
 * @desc 假设字符串str长度为N，想返回最长回文子串的长度 *
 *       时间复杂度O(N)
 *
 * @auther itliu
 * @date 2020/6/1
 */
public class Manacher {

    public static int manacher(String s) {
        //base case
        if (s == null || s.length() == 0) {
            return 0;
        }

        //加工字符串，避免虚轴情况
        char[] str = manacherString(s);
        //构建回文半径数组
        int[] pArr = new int[str.length];
        //中心点
        int C = -1;
        // 讲述中：R代表最右的扩成功的位置。coding中：最右的扩成功位置的，再下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;
        //复杂度的估算绑定到R的变化上，第一种大情况+第二种大情况的小情况3 最多能变化N，第二种大情况中情况1和情况2是O(1)的复杂度，
        //总的复杂度是O(N)
        for (int i = 0; i < str.length; i++) {
            /**
             * 2*C-i 为对称点， R-i为处理的当前字符到R右边界的距离
             * 第一种大情况: i在R外好理解， 就是R>i不成立，当前字符的回文只是它自己，然后往两边扩继续比对
             * 第二种大情况:
             *             1.i的对称点的回文左右边界严格在L..R内 --> i'多大你多大
             *             2.i的对应点的回文长度在L..R外 --> i的回文半径就是i..R的距离
             *             3.i的对应点的回文左边界在L外 --> 半径为i..R的串也一定是回文串,但是回文串会不会更长，R外的字符需要继续验证
             * 总结：下面这句话解决的是不用验的长度
             * 第一种大情况：i在R外，不用验的长度是1，继续往两边扩
             * 第二种大情况:
             *              1.i的对称点的回文左右边界严格在L..R内 --> i'多大你多大，不用验的距离就是i'的回文半径pArr[2 * C - i]
             *              2.i的对应点的回文长度在L..R外 --> i的回文半径就是i..R的距离，不用验的距离就是R-i
             *              3.i的对应点的回文左边界在L外 -->
             *                  半径为i..R的串也一定是回文串,但是回文串会不会更长，R外的字符需要继续验证
             *                  不用验的距离就是pArr[2 * C - i] == R - i，所以任取其一，R后面的继续验
             */
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            //在不越界的情况下继续验
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i] ++;
                } else {
                    break;
                }

                //每次R往右扩了都要更新，以i为中心的回文串长度往右扩回文串的长度，每次右边界都会在最右的扩成功位置的再下一个位置
                if (i + pArr[i] > R) {
                    R = i + pArr[i]; //R = i + pArr[i] - 1;
                    C = i;
                }
                max = Math.max(max, pArr[i]);
            }
        }
        return max - 1;
    }

    private static char[] manacherString(String s) {
        char[] charArr = s.toCharArray();
        char[] res = new char[charArr.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
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
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
