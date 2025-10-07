package dev.mattz.data.graphics.drawable_objects;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Point2D> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public void addPoint(int index, Point2D point) {
        points.add(index, point);
    }


    public Point2D getPoint(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }

    public void clear() {
        points.clear();
    }
}
