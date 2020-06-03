package org.itliu.algorithm.segmenttree;

import java.util.*;

/**
 * @desc 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 * 下面是这个游戏的简化版：
 * 1）只会下落正方形积木
 * 2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 * 4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 * 给定一个N*2的二维数组matrix，可以代表N个积木依次掉落， 返回每一次掉落之后的最大高度
 * @auther itliu
 * @date 2020/6/2
 */
public class FallingSquares {
    private static class SegmentTree {
        private int[] max;
        private int[] change;
        private boolean[] update;

        //size代表matrix的大小
        public SegmentTree(int size) {
            int N = size + 1;
            this.max = new int[N << 2];
            this.change = new int[N << 2];
            this.update = new boolean[N << 2];
        }

        private void pushUp(int rt) {
            this.max[rt] = Math.max(this.max[rt << 1], this.max[rt << 1 | 1]);
        }

        private void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                this.max[rt] = C;
                this.update[rt] = true;
                this.change[rt] = C;
                return;
            }

            int mid = (l + r) >> 1;
            pushDown(rt, (mid - l + 1), (r - mid));
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }

            int mid = (l + r) >> 1;
            pushDown(rt, (mid - l + 1), (r - mid));
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;

                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];

                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }
    }

    public static List<Integer> fallingSquares(int[][] positions) {
        //离散
        Map<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree tree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int C = arr[1];

            int height = tree.query(L, R, 1, N, 1) + C;
            max = Math.max(max, height);
            res.add(max);
            tree.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    private static Map<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }

        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }
}
