package dev.mattz.data.graphics.rasterizers.line;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Line;
import dev.mattz.data.graphics.drawable_objects.Point;
import dev.mattz.data.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface LineRasterizer extends Rasterizer {
    default void draw(int x1, int y1, int x2, int y2, Color color, BufferedImage bufferedImage) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.drawLine(x1, y1, x2, y2);
        graphics2D.dispose();
    }

    default void draw(Point start, Point end, Color color, BufferedImage bufferedImage) {
        draw(start.getX(), start.getY(), end.getX(), end.getY(), color, bufferedImage);
    }

    default void draw(Line line2D, BufferedImage bufferedImage) {
        draw(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2(), line2D.getColor(), bufferedImage);
    }

    @Override
    default void draw(Drawable drawable, BufferedImage bufferedImage) {
        Line line2D = (Line) drawable;
        draw(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2(), line2D.getColor(), bufferedImage);
    }
}

