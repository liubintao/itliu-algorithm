package org.itliu.algorithm.other;

/**
 * @desc: 鸡兔同笼问题
 * @author: itliu
 * @date: 2019/12/25 16:48
 * @version: 1.0
 */
public class ChickenWithRabbitCage {
    public static void main(String[] args) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                int a = x + y;
                int b = x * 2 + y * 4;
                if (a == 10 && b == 32) {
                    System.out.println("鸡：" + x + ", 兔：" + y);
                }
            }
        }
    }
}
