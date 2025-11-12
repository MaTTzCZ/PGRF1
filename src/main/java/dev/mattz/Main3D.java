package dev.mattz;

import dev.mattz.data.D3.vector;
import dev.mattz.data.D3.matrix.Matrix;
import dev.mattz.data.D3.matrix.MatrixIdentity;

public class Main3D {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(5, 5);
        matrix.fillWithRandom(1, 10);
        Matrix indentity = new MatrixIdentity(5);
        vector vector = new vector(1, 2 , 3, 4, 5);
        System.out.println(vector);
        printMatrix(matrix);
    }

    private static void printMatrix(Matrix matrix) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();
        for (int row = 0; row < rowCount; row++) {
            System.out.print("|");
            for (int column = 0; column < columnCount; column++) {
                System.out.printf(" %.2g", matrix.getValue(row, column));
            }
            System.out.println("|");
        }
    }
}
