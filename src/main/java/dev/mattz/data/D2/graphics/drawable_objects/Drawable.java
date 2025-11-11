package dev.mattz.data.D2.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public interface Drawable {
    List<Point2D> getAllPoints();
    Color getColor();
}
