package org.itliu.algorithm.segmenttree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @desc X轴上有N条线段，每条线段包括1个起点和终点。
 *       线段的重叠是这样来算的，[10 20]和[12 25]的重叠部分为[12 20]。
 *       给出N条线段的起点和终点，输出有多少条线段重合。
 * @auther itliu
 * @date 2020/6/3
 */
public class CoverMax_Line {

    private static int maxCover1(int[][] lines) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        //求出所有线段在X轴上的最左和最右坐标，边界
        for (int[] line : lines) {
            min = Math.min(min, line[0]);
            max = Math.max(max, line[1]);
        }

        int cover = 0;
        //用+0.5的方式，为了避免左线段右边界=右线段左边界的情况，用0.5的方式，公共区域如果有的话，必压中某个.5
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            //遍历所有线段
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cur, cover);
        }
        return cover;
    }

    public static int maxCover2(int[][] matrix) {
        Line[] lines = new Line[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            lines[i] = new Line(matrix[i][0], matrix[i][1]);
        }

        //将所有线段按线段的开始位置从小到大排序
        Arrays.sort(lines, new StartComparator());
        //将线段依次放入end排序的小根堆，排除掉不影响当前线段的线段
        PriorityQueue<Line> heap = new PriorityQueue<>(new EndComparator());

        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    private static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    private static class EndComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }
}
