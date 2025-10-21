package dev.mattz.data.graphics.rasterizers.line;

import dev.mattz.data.graphics.drawable_objects.Drawable;
import dev.mattz.data.graphics.drawable_objects.Line;
import dev.mattz.data.graphics.drawable_objects.Point2D;
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

    default void draw(Point2D start, Point2D end, Color color, BufferedImage bufferedImage) {
        draw(start.getX(), start.getY(), end.getX(), end.getY(), color, bufferedImage);
    }

    default void draw(Line line, BufferedImage bufferedImage) {
        draw(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), bufferedImage);
    }

    @Override
    default void draw(Drawable drawable, BufferedImage bufferedImage) {
        Line line = (Line) drawable;
        draw(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), bufferedImage);
    }
}

