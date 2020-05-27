package org.itliu.algorithm.reservoirsampling;

/**
 * @desc 蓄水池抽样算法
 *
 *       解决的问题：
 *       假设有一个源源吐出不同球的机器，
 *       只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
 *       如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
 *
 * @auther itliu
 * @date 2020/5/27
 */
public class ReservoirSampling {

    private static class RandomBox {
        //存储容器
        private int[] bag;
        //存储容器的容量
        private int N;
        //
        private int count;

        public RandomBox(int capacity) {
            this.bag = new int[capacity];
            N = capacity;
            count = 0;
        }

        private int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        private void add(int num) {
            count++;
            if (count <= N) {//100%能装下
                bag[count - 1] = num;
            } else {
                if (rand(count) <= N) { //概率进容器
                    //容器已满，进一个必须出一个，出同样是概率出
                    bag[rand(N) - 1] = num;
                }
            }
        }

        public int[] choices() {
            int[] ans = new int[N];
            for (int i = 0; i < bag.length; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println("begin!");
        int all = 100;
        int choose = 10;
        int testTimes = 50000;
        //收集多少次结果
        int[] counts = new int[all + 1];

        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(choose);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }

            final int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
//                System.out.print(ans[j] + " ");
            }
//            System.out.println();
        }

        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " times :" + counts[i]);
        }

        System.out.println("finish!");
    }
}
