package dev.mattz.data.D2.graphics.rasterizers.fillers;

import dev.mattz.data.D2.graphics.drawable_objects.Point2D;
import dev.mattz.data.D2.graphics.rasterizers.Rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public interface SeedFiller extends Rasterizer {
    default void spread(int x, int y, Color fillColor, BufferedImage bufferedImage, Stack<Point2D> pointsStack) {
        bufferedImage.setRGB(x, y, fillColor.getRGB());
        pointsStack.push(new Point2D(x - 1, y));
        pointsStack.push(new Point2D(x + 1, y));
        pointsStack.push(new Point2D(x, y - 1));
        pointsStack.push(new Point2D(x, y + 1));
    }
}
