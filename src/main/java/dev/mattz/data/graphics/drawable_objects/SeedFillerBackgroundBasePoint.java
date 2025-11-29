package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;

public class SeedFillerBackgroundBasePoint extends Point {
    private final Color fillColor;
    private final Color backgroundColor;

    public SeedFillerBackgroundBasePoint(int x, int y, Color fillColor, Color backgroundColor) {
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
