package org.itliu.algorithm.stack;

import java.util.Stack;

/**
 * @desc: 使用栈结构实现队列结构
 * @author: itliu
 * @date: 2020/4/21 16:20
 * @version: 1.0
 */
public class TwoStacksImplementQueue {

    public static class TwoStacksQueue {
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        public TwoStacksQueue() {
            this.stackPush = new Stack<>();
            this.stackPop = new Stack<>();
        }

        // push栈向pop栈倒入数据
        private void push2Pop() {
            if (this.stackPop.isEmpty()) {
                while (!this.stackPush.isEmpty()) {
                    this.stackPop.push(this.stackPush.pop());
                }
            }
        }

        public void add(int value) {
            this.stackPush.push(value);
            push2Pop();
        }

        public int poll() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            push2Pop();
            return this.stackPop.pop();
        }

        public int peek() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            push2Pop();
            return this.stackPop.peek();
        }
    }

    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}
