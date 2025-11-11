package dev.mattz.data.D3.matrix;

public class MatrixIdentity extends Matrix {
    public MatrixIdentity(int size) {
        super(size);
        for (int i = 0; i < size; i++) {
            setValue(i, i, 1);
        }
    }
}
