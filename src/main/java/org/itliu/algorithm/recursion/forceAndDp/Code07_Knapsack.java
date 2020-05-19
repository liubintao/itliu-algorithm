package org.itliu.algorithm.recursion.forceAndDp;

/**
 * @desc 从左往右的尝试模型
 * <p>
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?
 * @auther itliu
 * @date 2020/5/18
 */
public class Code07_Knapsack {

    public static int getMaxValue(int[] weight, int[] values, int bag) {
        return process(weight, values, 0, 0, bag);
    }

    //不变的参数是w[] v[] bag
    //只考虑index...的最大价值，0..index的最大价值已经确定
    // 0..index-1上做了货物的选择，使得你已经达到的重量是多少alreadyW
    // 如果返回-1，认为没有方案
    // 如果不返回-1，认为返回的值是真实价值
    private static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
        //base case
        if (alreadyW > bag) {
            return -1;
        }

        //重量没超，但是没货了，index..的最大价值为0
        if (index == w.length) {
            return 0;
        }

        //我要/不要当前货物，index..产生的最大价值
        //1.不要当前货物
        int p1 = process(w, v, index + 1, alreadyW, bag);
        //2.要当前货物
        int p2 = -1;
        int p2Next = process(w, v, index + 1, alreadyW + w[index], bag);
        if (p2Next != -1) {
            p2 = p2Next + v[index];
        }
        return Math.max(p1, p2);
    }


    public static int maxValue(int[] weight, int[] values, int bag) {
        return process2(weight, values, 0, bag);
    }

    // 只剩下rest的空间了，
    // index...货物自由选择，但是剩余空间不要小于0
    // 返回 index...货物能够获得的最大价值
    private static int process2(int[] w, int[] v, int index, int rest) {
        //base case 1
        if (rest < 0) {
            return -1;
        }

        //rest >=0 重量没超，但是没货了，index..的最大价值为0
        if (index == w.length) {//base case 2
            return 0;
        }

        // 有货也有空间
        int p1 = process2(w, v, index + 1, rest);
        int p2 = -1;
        int p2Next = process2(w, v, index + 1, rest - w[index]);
        if (p2Next != -1) {
            p2 = v[index] + p2Next;
        }

        return Math.max(p1, p2);
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7 };
        int[] values = { 5, 6, 3, 19 };
        int bag = 11;
        System.out.println(getMaxValue(weights, values, bag));
        System.out.println(maxValue(weights, values, bag));
//        System.out.println(dpWay(weights, values, bag));
    }
}
