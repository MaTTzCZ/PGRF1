package dev.mattz.data.D2.graphics.drawable_objects;

import java.awt.*;

public class SeedFillerBoundaryBasePoint extends Point2D {
    private final Color fillColor;
    private final Color backgroundColor;


    public SeedFillerBoundaryBasePoint(int x, int y, Color fillColor, Color backgroundColor) {
        super(x, y);
        this.fillColor = fillColor;
        this.backgroundColor = backgroundColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
