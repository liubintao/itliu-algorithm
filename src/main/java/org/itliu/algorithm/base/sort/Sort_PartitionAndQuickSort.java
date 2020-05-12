package org.itliu.algorithm.base.sort;

/**
 * @desc: Partition(包含荷兰国旗问题)和快速排序
 * @author: itliu
 * @date: 2020/4/22 11:01
 * @version: 1.0
 */
public class Sort_PartitionAndQuickSort {

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 把一个数组按照给定条件进行分区，左分区的数(可以无序)<=给定数，右分区的数(可以无序)>给定数
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return 1;
        }

        int lessEqual = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, ++lessEqual, index);
            }
            index++;
        }
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    // arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
    //  <arr[R]  ==arr[R]  > arr[R]
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }

        if (L == R) {
            return new int[]{L, R};
        }

        int less = L - 1; // < 区 右边界
        int more = R;     // > 区 左边界
        int index = L;

        while (index < more) {
            //左边界+1位置与index位置交换，左边界右扩，index++
            if (arr[index] < arr[R]) {
                swap(arr, ++less, index++);
            } else if (arr[index] == arr[R]) {
                index++;
            } else {
                //右边界-1位置与index位置交换，右边界左扩，index不变(因为index位置的数是交换过来的，还没看过)
                swap(arr, index, --more);
            }
        }
        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void quickSort1(int[] arr) {
        //base case
        if (arr == null || arr.length < 2) {
            return;
        }

        process1(arr, 0, arr.length - 1);
    }

    private static void process1(int[] arr, int L, int R) {
        //base case
        if (L >= R) {
            return;
        }

        //L..R partition  arr[R]  [<=arr[R]  arr[R] >arr[R]]
        int M = partition(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    public static void quickSort2(int[] arr) {
        //base case
        if (arr == null || arr.length < 2) {
            return;
        }

        process2(arr, 0, arr.length - 1);
    }

    private static void process2(int[] arr, int L, int R) {
        //base case
        if (L >= R) {
            return;
        }

        //L..R partition  arr[R]  [<=arr[R]  arr[R] >arr[R]]
        //先找出==给定值的边界
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    public static void quickSort3(int[] arr) {
        //base case
        if (arr == null || arr.length < 2) {
            return;
        }

        process3(arr, 0, arr.length - 1);
    }

    private static void process3(int[] arr, int L, int R) {
        //base case
        if (L >= R) {
            return;
        }

        //随意选择一个数与arr[R]交换，再拿arr[R]进行partition
        swap(arr, L + ((int) (Math.random() * (R - L + 1))), R);
        //L..R partition  arr[R]  [<=arr[R]  arr[R] >arr[R]]
        //先找出==给定值的边界
        int[] equalArea = netherlandsFlag(arr, L, R);
        process3(arr, L, equalArea[0] - 1);
        process3(arr, equalArea[1] + 1, R);

    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 5;
        int maxSize = 10;
        int maxValue = 10;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }
}
