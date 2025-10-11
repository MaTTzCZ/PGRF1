package dev.mattz.data.graphics.drawable_objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Polygon implements Drawable {
    private LinkedList<Point2D> points;

    public Polygon() {
        points = new LinkedList<>();
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

    @Override
    public List<Point2D> getAllPoints() {
        return points;
    }
}
