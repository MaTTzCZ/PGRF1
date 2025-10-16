package dev.mattz.data.graphics.rasterizers.gradient_line;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.GradientLine;
import dev.mattz.data.graphics.drawable_objects.Point2D;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GradientLineRasterizer extends Rasterizer {
    default void draw(int x1, int y1, int x2, int y2, Color color1, Color color2, BufferedImage bufferedImage) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(new GradientPaint(x1, y1, color1, x2, y2, color2));
        graphics2D.drawLine(x1, y1, x2, y2);
        graphics2D.dispose();
    }

    default void draw(Point2D start, Point2D end, Color color1, Color color2, BufferedImage bufferedImage) {
        draw(start.x(), start.y(), end.x(), end.y(), color1, color2, bufferedImage);
    }

    default void draw(GradientLine line, BufferedImage bufferedImage) {
        draw(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), line.getColor2(), bufferedImage);
    }

    @Override
    default void draw(Drawable drawable, BufferedImage bufferedImage) {
        GradientLine line = (GradientLine) drawable;
        draw(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), line.getColor2(), bufferedImage);
    }

    default Color interpolateColor(Color c1, Color c2, float t) {
        int r = (int) (c1.getRed() + t * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + t * (c2.getBlue() - c1.getBlue()));
        return new Color(r, g, b);
    }
}

