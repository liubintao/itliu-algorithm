package org.itliu.algorithm.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @desc:
 * @author: itliu
 * @date: 2020/1/7 17:57
 * @version: 1.0
 */
public class CountSortTest {

    int[] generateRandomArray() {
        Random r = new Random();

        int[] arr = new int[10000];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(10);
        }

        return arr;
    }

    @Test
    public void testSort() {
        int[] arr = generateRandomArray();
        //todo 实际的排序调用
        int[] result = null;
//      result = Sort_Selection.sort(arr);

        Arrays.sort(arr);

        boolean same = true;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != result[i]) {
                same = false;
            }
        }

        assertEquals(true, same);
    }
}
