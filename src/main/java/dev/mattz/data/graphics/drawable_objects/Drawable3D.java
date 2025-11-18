package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;
import java.util.List;

public interface Drawable3D {
    List<Point3D> getAllPoints();
    Color getColor();
}
