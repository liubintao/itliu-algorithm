package org.itliu.algorithm.segmenttree;

/**
 * @desc 1.一种支持范围整体修改和范围整体查询的数据结构
 * 2.解决的问题范畴： 大范围信息可以只由左、右两侧信息加工出， 而不必遍历左右两个子范围的具体状况
 * <p>
 * 实例一：
 * 给定一个数组arr，用户希望你实现如下三个方法
 * 1）void add(int L, int R, int V) :  让数组arr[L…R]上每个数都加上V
 * 2）void update(int L, int R, int V) :  让数组arr[L…R]上每个数都变成V
 * 3）int sum(int L, int R) :让返回arr[L…R]这个范围整体的累加和
 * 怎么让这三个方法，时间复杂度都是O(logN)
 * @auther itliu
 * @date 2020/6/2
 */
public class AccumulationSum {
    private static class SegmentTree {
        // arr[]为原序列的信息从0开始，但在arr里是从1开始的，为了更快的使用位运算
        // sum[]模拟线段树维护区间和
        // lazy[]为累加懒惰标记
        // change[]为更新的值
        // update[]为更新慵懒标记
        private static int MAX_N;
        private int arr[];
        private int sum[];
        private int lazy[];
        private int change[];
        private boolean update[];

        public SegmentTree(int[] origin) {
            MAX_N = origin.length + 1; //因为0位置我不用，所以长度要+1
            this.arr = new int[MAX_N]; // arr[0] 不用  从1开始使用
            for (int i = 1; i < MAX_N; i++) {
                this.arr[i] = origin[i - 1];
            }
            //根据等比数列求和可知，树的节点个数不会超过n*2，所以给n*2足够用,
            // 但是对于数组长度不是2^n的最差情况，比如长度为2^n + 1，为了维持满二叉树，最下层节点要扩成 2 * 2^n，总结点数就是4*2^n
            // 再基于上面的理论，5扩成不小于8再*2，也就是直接用长度*4，肯定够用
            this.sum = new int[MAX_N << 2];// 用来支持脑补概念中，某一个范围的累加和信息
            this.lazy = new int[MAX_N << 2];// 用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
            this.change = new int[MAX_N << 2];// 用来支持脑补概念中，某一个范围有没有更新操作的任务
            this.update = new boolean[MAX_N << 2];// 用来支持脑补概念中，某一个范围更新任务，更新成了什么
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // 在初始化阶段，先把sum数组，填好
        // 在arr[l~r]范围上，去build，1~N，
        // rt :  这个范围在sum中的下标
        private void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            //往上推
            pushUp(rt);
        }

        // L..R -> 任务范围 ,所有的值累加上C
        // l,r -> 表达的范围
        // rt  去哪找l，r范围上的信息
        private void add(int L, int R, int C, int l, int r, int rt) {
            // 任务的范围彻底覆盖了，当前表达的范围
            if (L <= l && r <= R) {
                //这个节点下面有多少个节点，就有多少个节点*C的值
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }

            // 任务不能把l..r全包住
            // 要把任务往下发
            // 任务  L, R  没有把本身表达范围 l,r 彻底包住
            int mid = (l + r) >> 1;
            // 下发之前的lazy add任务
            pushDown(rt, mid - l + 1, r - mid);
            // 左孩子是否需要接到任务(L,R,C)
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            // 右孩子是否需要接到任务(L,R,C)
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 左右孩子做完任务后，我更新我的sum信息
            pushUp(rt);
        }

        /**
         * 后三个参数永远不变
         */
        private void update(int L, int R, int C, int l, int r, int rt) {
            //能不能懒住
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                sum[rt] = C * (r - l + 1);
                lazy[rt] = 0;
                return;
            }

            // 当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;
            //update下发
            pushDown(rt, (mid - l + 1), (r - mid));
            //任务能不能给左孩子
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            //任务能不能给右孩子
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        private long query(int L, int R, int l, int r, int rt) {
            //能不能懒住
            if (L <= l && r <= R) {
                return sum[rt];
            }

            // 当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;
            //update下发
            pushDown(rt, (mid - l + 1), (r - mid));
            long ans = 0;
            //任务能不能给左孩子
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            //任务能不能给右孩子
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        //之前的所有懒增加和懒更新，从父范围，发给左右两个子范围
        // ln表示左子树元素结点个数，rn表示右子树结点个数
        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                //update下发
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                //change下发
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                //sum下发
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                //lazy清空
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                //重置父节点的update
                update[rt] = false;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];//将父节点的lazy下发给左孩子
                sum[rt << 1] += lazy[rt] * ln;//左节点的累加和=左节点下面的节点数 * 父节点lazy值

                lazy[rt << 1 | 1] += lazy[rt];//将父节点的lazy下发给右孩子
                sum[rt << 1 | 1] += lazy[rt] * rn;//右节点的累加和=右节点下面的节点数 * 父节点lazy值

                lazy[rt] = 0;//父节点的lazy已下发，清空父节点的lazy，下发之后左右孩子也揽住
            }
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}
