package dev.mattz.data.graphics.rasterizers;

import dev.mattz.data.graphics.drawable_objects.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface LineRasterizer {
    default void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setPaint(new GradientPaint(
                start.getX(), start.getY(), color1,
                end.getX(), end.getY(), color2
        ));
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        g2d.dispose();
    }

    default Color interpolateColor(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() + t * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + t * (c2.getBlue() - c1.getBlue()));
        return new Color(r, g, b);
    }
}

