package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public class Point3D implements Drawable3D {
    private int x, y, z, w;

    public Point3D() {
        this.x = this.y = this.z = 0;
        this.w = 1;
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public List<Point3D> getAllPoints() {
        return List.of(this);
    }

    @Override
    public Color getColor() {
        return null;
    }
}
