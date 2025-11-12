package dev.mattz.data.D3.matrix;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class Matrix {
    private final double[][] matrix;

    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns) {
        matrix = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    public Matrix(int size) {
        matrix = new double[size][size];
        this.rows = this.columns = size;
    }

    public Matrix transpose() {
        Matrix newMatrix = new Matrix(columns, rows);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix.setValue(i, j, matrix[j][i]);
            }
        }
        return newMatrix;
    }

    public Optional<Matrix> multiply(Matrix matrix){
        if (rows != matrix.columns)
            return Optional.empty();
    }

    public void fillWithRandom(int min, int max) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Random r = new Random();
                int randomInt = r.nextInt(min, max);
                matrix[row][column] = randomInt;
            }
        }
    }

    public double getValue(int row, int column) {
        return matrix[row][column];
    }

    public int getRowCount() {
        return rows;
    }

    public int getColumnCount() {
        return columns;
    }

    public void setValue(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public boolean isSquare() {
        return rows == columns;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix);
    }
}
