package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;

public class FillerBackgroundBasePoint extends Point2D {
    private final Color backgroundColor;
    private final Color fillColor;

    public FillerBackgroundBasePoint(int x, int y, Color backgroundColor, Color fillColor) {
        super(x, y);
        this.backgroundColor = backgroundColor;
        this.fillColor = fillColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getFillColor() {
        return fillColor;
    }
}
