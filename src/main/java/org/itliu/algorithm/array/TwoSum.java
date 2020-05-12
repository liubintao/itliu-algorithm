package org.itliu.algorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 给出一个int数组，返回其中两个数相加等于目标值的索引下标
 */
public class TwoSum {

    /**
     * 时间复杂度为O(N^2)
     * @param sums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] sums, int target) {
        for(int i = 0; i < sums.length; i++) {
            for(int j = 1; j < sums.length; j++) {
                if(sums[i] + sums[j] == target) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }

    /**
     * 时间复杂度为O(N)
     * @param sums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] sums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < sums.length; i++) {
            map.put(sums[i], i);
        }
        for(int i = 0; i < sums.length; i++) {
            int complement = target - sums[i];
            if(map.containsKey(complement) && map.get(complement) != i) {
                return new int[] {i, map.get(complement)};
            }
        }
        return new int[0];
    }

    public static int[] twoSum2(int[] sums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < sums.length; i ++) {
            int complement = target - sums[i];
            if(map.containsKey(complement)) {
                return new int[] {i, map.get(complement)};
            }
            map.put(sums[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] array = new int[] {2,7,11,15};
        long start = System.currentTimeMillis();
        twoSum(array, 9);
        System.out.println(System.currentTimeMillis() - start);

        long start1 = System.currentTimeMillis();
        twoSum1(array, 9);
        System.out.println(System.currentTimeMillis() - start1);

        long start2 = System.currentTimeMillis();
        twoSum1(array, 9);
        System.out.println(System.currentTimeMillis() - start2);
    }
}
