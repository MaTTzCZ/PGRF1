package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public class Line implements Drawable {
    private final int x1, y1, x2, y2;
    private final Color color;

    public Line(Point2D start, Point2D end, Color color) {
        this.x1 = start.getX();
        this.y1 = start.getY();
        this.x2 = end.getX();
        this.y2 = end.getY();
        this.color = color;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public List<Point2D> getAllPoints() {
        return List.of(new Point2D(x1, y1), new Point2D(x2, y2));
    }
}
