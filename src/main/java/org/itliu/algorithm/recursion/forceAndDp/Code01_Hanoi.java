package org.itliu.algorithm.recursion.forceAndDp;

import java.util.Set;
import java.util.Stack;

/**
 * @desc 打印n层汉诺塔从最左边移动到最右边的全部过程
 * @auther itliu
 * @date 2020/5/18
 */
public class Code01_Hanoi {

    private static void hanoi1(int n) {
        left2Right(n);
    }

    //请把1..N层圆盘从最左 -> 最右
    private static void left2Right(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        left2Middle(n - 1);
        System.out.println("Move " + n + " from left to right");
        middle2Right(n - 1);
    }

    private static void left2Middle(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to middle");
            return;
        }
        left2Right(n - 1);
        System.out.println("Move " + n + " from left to middle");
        right2Middle(n - 1);
    }

    private static void right2Middle(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to middle");
            return;
        }
        right2Left(n - 1);
        System.out.println("Move " + n + " from right to left");
        left2Middle(n - 1);
    }

    private static void right2Left(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        right2Middle(n - 1);
        System.out.println("Move " + n + " from right to left");
        middle2Left(n - 1);
    }

    private static void middle2Left(int n) {
        if (n == 1) {
            System.out.println("Move 1 from middle to left");
            return;
        }
        middle2Right(n - 1);
        System.out.println("Move " + n + " from middle to left");
        right2Left(n - 1);
    }

    private static void middle2Right(int n) {
        if (n == 1) {
            System.out.println("Move 1 from middle to right");
            return;
        }
        middle2Left(n - 1);
        System.out.println("Move " + n + " from middle to right");
        left2Right(n - 1);
    }

    private static void hanoi2(int n) {
        if (n > 0) {
            fun(n, "left", "right", "middle");
        }
    }

    private static void fun(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to" + to);
            return;
        }
        fun(n - 1, from, other, to);
        System.out.println("Move " + n + " from " + from + " to" + to);
        fun(n - 1, other, to, from);
    }

    public static class Record {
        public boolean finish1;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean f1, int b, String f, String t, String o) {
            finish1 = false;
            base = b;
            from = f;
            to = t;
            other = o;
        }
    }

    public static void hanoi3(int N) {
        if (N < 1) {
            return;
        }
        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, N, "left", "right", "mid"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish1 = true;
                }
            } else {
                if (!cur.finish1) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }


    public static void main(String[] args) {
        int n = 3;
        hanoi1(n);
        System.out.println("============");
        hanoi2(n);
        System.out.println("============");
        hanoi3(n);
    }
}
