package org.itliu.algorithm.matrixprocess;

/**
 * @desc 转圈打印矩阵
 * @auther itliu
 * @date 2020/6/8
 */
public class PrintMatrixSpiralOrder {
    private static void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    private static void printEdge(int[][] matrix, int tR, int tC, int dR, int dC) {
        //只剩一行
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.println(matrix[tR][i] + " ");
            }
        } else if (tC == dC){  //只剩一列
            for (int i = tR; i <= dR; i++) {
                System.out.println(matrix[i][tC] + " ");
            }
        } else {
            int curC = tC;
            int curR = tR;
            while (curC != dC) {
                System.out.print(matrix[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) {
                System.out.print(matrix[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) {
                System.out.print(matrix[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) {
                System.out.print(matrix[curR][tC] + " ");
                curR--;
            }
        }
//        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }
}
