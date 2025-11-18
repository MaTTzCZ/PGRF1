package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PencilStroke implements Drawable2D {
    private final ArrayList<Point2D> points;
    private final Color color;

    public PencilStroke(Color color) {
        points = new ArrayList<>();
        this.color = color;
    }

    public void addPoint(Point2D point){
        points.add(point);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public List<Point2D> getAllPoints() {
        return points;
    }
}
