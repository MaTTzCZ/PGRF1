package dev.mattz.data.graphics.drawable_objects;

import java.util.List;

public class Dash implements Drawable {
    private Point2D start;
    private Point2D end;

    public Dash(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }



    @Override
    public List<Point2D> getAllPoints() {
        return List.of(start, end);
    }
}
