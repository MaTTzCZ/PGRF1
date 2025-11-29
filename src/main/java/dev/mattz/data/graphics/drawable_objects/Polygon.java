package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Polygon implements Drawable {
    private final LinkedList<Point> points;
    private final Color color;

    public Polygon(Color color) {
        points = new LinkedList<>();
        this.color = color;
    }

    public void addPoint(Point point) {
        points.add(point);
    }


    public Point getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

    @Override
    public List<Point> getAllPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }
}
