package dev.mattz.data.graphics.drawable_objects;

import java.util.List;

public class Point2D implements Drawable {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
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

    @Override
    public List<Point2D> getAllPoints() {
        return List.of(this);
    }
}
