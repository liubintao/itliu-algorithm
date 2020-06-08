package org.itliu.algorithm.matrixprocess;

/**
 * @desc 原地旋转正方形矩阵
 * @auther itliu
 * @date 2020/6/8
 */
public class RotateMatrix {
    private static void rotate(int[][] matrix) {
        //(a,b)左上角坐标
        int a = 0;
        int b = 0;
        //(c,d)右下角坐标
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    private static void rotateEdge(int[][] matrix, int a, int b, int c, int d) {
        int tmp = 0;
        for (int i = 0; i < d - b; i++) {
            tmp = matrix[a][b + i];
            matrix[a][b + i] = matrix[c - i][b];
            matrix[c - i][b] = matrix[c][d - i];
            matrix[c][d - i] = matrix[a + i][d];
            matrix[a + i][d] = tmp;
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(matrix);
        System.out.println("==================");
        rotate(matrix);
        printMatrix(matrix);
    }
}
