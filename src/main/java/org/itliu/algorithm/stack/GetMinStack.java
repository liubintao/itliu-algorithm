package org.itliu.algorithm.stack;

import java.util.Stack;

/**
 * @desc: 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * <p>
 * 1）pop、push、getMin操作的时间复杂度都是 O(1)。
 * 2）设计的栈类型可以使用现成的栈结构。
 * @author: itliu
 * @date: 2020/4/21 15:52
 * @version: 1.0
 */
public class GetMinStack {

    /**
     * 使用两个栈，数据栈中每压栈一次，与最小栈栈顶的数相比，<=最小栈栈顶的数则压栈，出栈时数据栈栈顶的数与最小栈栈顶的数比较，相等
     * 则同步出栈，不等则只有数据栈出栈
     */
    public static class MyStack1 {
        public Stack<Integer> dataStack;
        public Stack<Integer> minStack;

        public MyStack1() {
            this.dataStack = new Stack<>();
            this.minStack = new Stack<>();
        }

        public void push(int value) {
            if (minStack.isEmpty()) {
                this.minStack.push(value);
            } else if (value <= getMin()) {
                this.minStack.push(value);
            }
            this.dataStack.push(value);
        }

        public int pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }

            int val = this.dataStack.pop();
            if (val == this.getMin()) {
                this.minStack.pop();
            }
            return val;
        }

        private int getMin() {
            if (this.minStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.minStack.peek();
        }
    }

    /**
     * 使用两个栈，数据栈中每压栈一次，最小栈中也压栈一次，压栈的时间复杂度为O(1)
     */
    public static class MyStack2 {
        public Stack<Integer> dataStack;
        public Stack<Integer> minStack;

        public MyStack2() {
            this.dataStack = new Stack<>();
            this.minStack = new Stack<>();
        }

        public void push(int value) {
            /**
             * 要压栈的数与最小栈的栈顶相比，谁小把谁压到最小栈，如果最小栈为空，直接将value压到数据栈和最小栈
             */
            if (minStack.isEmpty()) {
                this.minStack.push(value);
            } else if (value < this.getMin()) {
                this.minStack.push(value);
            }
            this.dataStack.push(value);
        }

        public int pop() {
            if (this.dataStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            this.minStack.pop();
            return this.dataStack.pop();
        }

        private int getMin() {
            if (this.minStack.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.minStack.peek();
        }
    }

    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(3);
        System.out.println(stack1.getMin());
        stack1.push(4);
        System.out.println(stack1.getMin());
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());

        System.out.println("=============");

        MyStack1 stack2 = new MyStack1();
        stack2.push(3);
        System.out.println(stack2.getMin());
        stack2.push(4);
        System.out.println(stack2.getMin());
        stack2.push(1);
        System.out.println(stack2.getMin());
        System.out.println(stack2.pop());
        System.out.println(stack2.getMin());
    }
}
