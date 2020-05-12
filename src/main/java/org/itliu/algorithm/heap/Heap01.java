package org.itliu.algorithm.heap;

/**
 * @desc:
 * @author: itliu
 * @date: 2020/4/22 19:34
 * @version: 1.0
 */
public class Heap01 {

    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            this.heapSize = 0;
        }

        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        public boolean isFull() {
            return this.heapSize == this.limit;
        }

        public void push(int value) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            // value  heapSize
            heapInsert(heap, heapSize++);
        }

        // 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
        // 剩下的数，依然保持大根堆组织
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        //下沉
        // 从index位置，往下看，不断的下沉，
        // 停：我的孩子都不再比我大；已经没孩子了
        private void heapify(int[] heap, int index, int heapSize) {
            int left = index * 2 + 1;
            //左节点没有越界
            while (left < heapSize) {
                // 左右两个孩子中，谁大，谁把自己的下标给largest
                // 右  ->  1) 有右孩子   && 2）右孩子的值比左孩子大才行
                // 否则，左
                int largest = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                //孩子节点中较大的那个和父节点谁大
                largest = heap[largest] > heap[index] ? largest : index;
                //不再下沉的条件
                if (largest == index) {
                    break;
                }

                swap(heap, index, largest);
                index = largest;
                left = index * 2 + 1;
            }
        }

        //上浮
        private void heapInsert(int[] heap, int index) {
            // arr[index]
            // 终止条件：arr[index] 不比 arr[index父]大了 ， 停
            // index = 0;
            while (heap[index] > heap[(index - 1) / 2]) {
                swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * 暴力方法的最大堆
     */
    public static class RightMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public RightMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            this.heapSize = 0;
        }

        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        public boolean isFull() {
            return this.heapSize == limit;
        }

        public void push(int value) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < heapSize; i++) {
                if (heap[i] > heap[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = heap[maxIndex];
            //交换，避免删除移动数据
            heap[maxIndex] = heap[--heapSize];
            return ans;
        }
    }

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
//                        System.out.println(ToStringBuilder.reflectionToString(my.heap));
//                        System.out.println(ToStringBuilder.reflectionToString(test.heap));
                        int a = my.pop();
                        int b = test.pop();
//                        System.out.println("a:" + a + " b:" + b);
                        if (a != b) {
                            System.out.println("Oops!");
                        }
//                        if (my.pop() != test.pop()) {
//                            System.out.println("Oops!");
//                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
