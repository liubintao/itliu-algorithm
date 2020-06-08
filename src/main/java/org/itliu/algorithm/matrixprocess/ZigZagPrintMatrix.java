package org.itliu.algorithm.matrixprocess;

/**
 * @desc zigzag打印矩阵
 * @auther itliu
 * @date 2020/6/8
 */
public class ZigZagPrintMatrix {

    private static void printMatrixZigZag(int[][] matrix) {
        int aR = 0;//a坐标行 -> aRow
        int aC = 0;//a坐标列 -> aColumn
        int bR = 0;//b坐标行 -> bRow
        int bC = 0;//b坐标列 -> bColumn

        int endR = matrix.length - 1;//结尾行
        int endC = matrix[0].length - 1;//结尾列
        boolean fromUp = false;//第一次从上往下打印
        while (aR != endR + 1) {
            printLevel(matrix, aR, aC, bR, bC, fromUp);

            aR = aC == endC ? aR + 1 : aR;
            aC = aC == endC ? aC : aC + 1;

            bC = bR == endR ? bC + 1 : bC;
            bR = bR == endR ? bR : bR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    //给了坐标和方向
    private static void printLevel(int[][] matrix, int aR, int aC, int bR, int bC, boolean fromUp) {
        if (fromUp) { //从上往下打印
            while (aR != bR + 1) {
                System.out.print(matrix[aR++][aC--] + " ");
            }
        } else { //从下往上打印
            while (bR != aR - 1) {
                System.out.print(matrix[bR--][bC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printMatrixZigZag(matrix);

    }
}
