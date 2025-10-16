package dev.mattz.data.graphics.drawable_objects;

import java.awt.*;

public class GradientLine extends Line {
    private final Color color2;

    public GradientLine(Point2D start, Point2D end, Color color1, Color color2) {
        super(start, end, color1);
        this.color2 = color2;
    }

    public Color getColor2() {
        return color2;
    }
}
