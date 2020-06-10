package org.itliu.algorithm.longestsumsubarray;

/**
 * @desc
 *
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
 * 返回其长度
 *
 * @auther itliu
 * @date 2020/6/10
 */
public class LongestLessSumSubArrayLength {

    private static int maxLengthAwesome(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;

        int[] minSums = new int[N];//最小和数组，每个位置都试试能不能往右扩
        int[] minSumEnds = new int[N];//最小和数组的结束位置

        minSums[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;

        //预处理结构
        for (int i = N - 2; i >= 0; i--) {
            //能否往右扩,右边的累加和<0，不影响当前结果的情况下可以右扩
            if (minSums[i + 1] <= 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }

        int end = 0;
        int sum = 0;
        int len = 0;
        // i是窗口的最左的位置，end扩出来的最右有效块儿的最后一个位置的，再下一个位置
        // end也是下一块儿的开始位置
        // 窗口：[i~end)
        for (int i = 0; i < N; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
            while (end < N && sum + minSums[end] <= K) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }

            len = Math.max(len, end - i);

            if (end > i) {//[i..end)窗口内还有数
                sum -= arr[i];
            } else {
                end = i + 1;
            }
        }
        return len;
    }

    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
