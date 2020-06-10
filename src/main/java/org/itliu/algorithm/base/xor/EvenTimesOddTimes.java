package org.itliu.algorithm.base.xor;

/**
 * @desc: 异或的两大性质：1) 0^N == N      N^N == 0
 * 2) 异或运算满足交换律和结合率
 * 1、一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 * 2、怎么把一个int类型的数，提取出最右侧的1来
 * 3、一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 * @author: itliu
 * @date: 2020/4/20 16:57
 * @version: 1.0
 */
public class EvenTimesOddTimes {
    /**
     * arr中，只有一种数，出现奇数次
     * 其他出现偶数次的数相互抵消，因为异或的性质:0^N=N，N^N=0
     */
    public static void printOddTimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    /**
     * arr中，有两种数，出现奇数次
     *
     * @param arr 数组
     */
    public static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // eor = a ^ b
        // 两种数出现奇数次，所以 eor != 0
        // eor必然有一个位置上是1
        // 0110010000
        // 0000010000

        int rightOne = eor & (~eor + 1);// 提取出最右的1
        int onlyOne = 0;//eor'

        for (int i = 0; i < arr.length; i++) {
            //只取两种奇数中的一种
            //  arr[1] =  111100011110000
            // rightOne=  000000000010000
            //条件是==0 || !=0 都可以，都是只取奇数中的一种
            if ((rightOne & arr[i]) == 0) {
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (onlyOne ^ eor));
    }

    /**
     * 统计一个数由多少个二进制数1
     * 思路：
     * 1、取出n最右侧的1，记为rightOne
     * 2、消除最右侧的1，n^rightOne，使用异或的性质：不进位加
     *
     * @param n
     */
    public static int bit1Count(int n) {
        int count = 0;

        //   011011010000
        //   000000010000     1

        //   011011000000

        //
        while (n != 0) {
            int rightOne = n & (~n + 1);
            count++;

            n ^= rightOne;
        }
        return count;
    }

    public static void main(String[] args) {
        int a = 5;
        int b = 7;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

    }
}
