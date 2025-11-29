package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public interface Drawable {
    List<Point> getAllPoints();
    Color getColor();
}
