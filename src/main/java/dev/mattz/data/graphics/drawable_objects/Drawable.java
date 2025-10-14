package dev.mattz.data.graphics.drawable_objects;

import java.util.List;

public interface Drawable {
    void draw();
    List<Point2D> getAllPoints();
}
