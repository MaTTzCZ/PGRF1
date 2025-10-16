package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Polygon implements Drawable {
    private final LinkedList<Point2D> points;
    private final Color color;

    public Polygon(Color color) {
        points = new LinkedList<>();
        this.color = color;
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }


    public Point2D getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

    @Override
    public List<Point2D> getAllPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }
}
