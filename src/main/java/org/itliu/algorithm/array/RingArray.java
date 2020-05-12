package org.itliu.algorithm.array;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @desc: 怎么用数组实现不超过固定大小的队列和栈？栈：正常使用，队列：环形数组
 * @author: itliu
 * @date: 2020/4/21 15:03
 * @version: 1.0
 */
public class RingArray {
    /**
     * 使用数组实现的队列--先进先出
     * 使用两个索引变量pushIndex和pollIndex，和一个容器大小变量size进行控制
     */
    public static class MyQueue {
        public int[] arr;
        public int pushIndex;
        public int pollIndex;
        public int size;
        public int limit;

        public MyQueue(int limit) {
            this.arr = new int[limit];
            this.pushIndex = 0;
            this.pollIndex = 0;
            this.size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("栈满了，不能再加了");
            }
            size++;
            arr[pushIndex] = value;
            pushIndex = getNextIndex(pushIndex);
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("栈空了，不能再拿了");
            }
            size--;
            int ans = arr[pollIndex];
            pollIndex = getNextIndex(pollIndex);
            return ans;
        }

        // 如果现在的下标是index，返回下一个位置
        private int getNextIndex(int index) {
            return index < limit - 1 ? index + 1 : 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    /**
     * 使用数组实现的栈--后进先出
     */
    public static class MyStack {
        public int[] arr;
        public int index;
        public int size;

        public MyStack(int limit) {
            this.arr = new int[limit];
            this.index = 0;
            this.size = 0;
        }

        public void push(int value) {
            if (size == arr.length) {
                throw new RuntimeException("栈满了，不能再加了");
            }
            size++;
            arr[index++] = value;
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("栈空了，不能再拿了");
            }
            size--;
            int ans = arr[--index];
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack myStack = new MyStack(oneTestDataNum);
            MyQueue myQueue = new MyQueue(oneTestDataNum);
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!stack.isEmpty() && !isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!queue.isEmpty() && !isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }
}
