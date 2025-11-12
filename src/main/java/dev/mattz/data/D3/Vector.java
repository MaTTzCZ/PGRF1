package dev.mattz.data.D3;

import java.util.Optional;

public class Vector {
    double[] components;

    public Vector(double... components) {
        this.components = components;
    }

    public Optional<Vector> add(Vector vector) {
        if (components.length != vector.components.length)
            return Optional.empty();
        else {
            double[] newVectorComponents = new double[components.length];
            for (int i = 0; i < components.length; i++) {
                newVectorComponents[i] = components[i] + vector.components[i];
            }
            return Optional.of(new Vector(newVectorComponents));
        }
    }

    public Optional<Vector> subtract(Vector vector) {
        if (components.length != vector.components.length)
            return Optional.empty();
        else {
            double[] newVectorComponents = new double[components.length];
            for (int i = 0; i < components.length; i++) {
                newVectorComponents[i] = components[i] - vector.components[i];
            }
            return Optional.of(new Vector(newVectorComponents));
        }
    }

    public Optional<Vector> subtract(double multiplier) {
        double[] newVectorComponents = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            newVectorComponents[i] = components[i] * multiplier;
        }
        return Optional.of(new Vector(newVectorComponents));
    }

    public Optional<Double> dot(Vector vector) {
        if (components.length != vector.components.length)
            return Optional.empty();
        else {
            double sum = 0;
            for (int i = 0; i < components.length; i++) {
                sum = sum + components[i] * vector.components[i];
            }
            return Optional.of(sum);
        }
    }


}
