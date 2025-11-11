package dev.mattz;

import dev.mattz.data.D3.matrix.Matrix;

public class Main3D {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(5, 5);
        System.out.println(matrix);
    }

    private static void printMatrix(Matrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        for (int row = 0; row < rowCount; row++) {
            System.out.print("|");
            for (int column = 0; column < columnCount; column++) {
                System.out.printf(" %2f ", matrix.getValue(row, column));
            }
            System.out.println("|");
        }
    }
}
