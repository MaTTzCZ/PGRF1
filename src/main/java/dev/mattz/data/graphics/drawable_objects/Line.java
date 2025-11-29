package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public class Line implements Drawable {
    private final Point start, end;
    private final Color color;

    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public int getX1() {
        return start.getX();
    }

    public int getY1() {
        return start.getY();
    }

    public int getX2() {
        return end.getX();
    }

    public int getY2() {
        return end.getY();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public List<Point> getAllPoints() {
        return List.of(start, end);
    }
}
